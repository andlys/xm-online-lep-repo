package function


import com.icthh.xm.commons.security.XmAuthenticationContext
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

def log = LoggerFactory.getLogger(getClass())

String email = lepContext.inArgs?.functionInput?.email
String firstName = lepContext.inArgs?.functionInput?.firstName
String lastName = lepContext.inArgs?.functionInput?.lastName

String uaaApiEndpoint = lepContext.services.tenantConfigService.getConfig().uaaApiEndpoint
XmAuthenticationContext authContext = lepContext.authContext
String authorization = authContext.getTokenType().get() + ' ' + authContext.getTokenValue().get()
RestTemplate restTemplate = lepContext.templates.rest


class BusinessException {

    String path
    String message

    BusinessException(String path, String message) {
        this.path = path
        this.message = message
    }
}

def isAccountExists = {
    String url = uaaApiEndpoint + '/api/users/logins?login=' + email
    RequestEntity req = RequestEntity.get(URI.create(url)).header(HttpHeaders.AUTHORIZATION, authorization).build()
    ResponseEntity<Map> resp = restTemplate.exchange(req, Map.class)
    if (resp.statusCode == HttpStatus.OK) {
        throw new BusinessException('error.account-exists', 'Account exists')
    }
    return resp.body
}

isAccountExists()


def createAccount = {
    def user = [
            "firstName": firstName,
            "lastName": lastName,
            "langKey": "en",
            "logins": [
                    [
                            "typeKey": "LOGIN.EMAIL",
                            "login": email
                    ]
            ]
    ]

    String url = uaaApiEndpoint + '/api/users/'
    RequestEntity req = RequestEntity.post(URI.create(url)).header(HttpHeaders.AUTHORIZATION, authorization).body(user)
    try {
        restTemplate.exchange(req, String.class)
        log.info('SUCCESS: Successfully account created {}', user)
    } catch (RestClientException e) {
        log.error('FAILURE: Could not create account: {}. Exception: {}', user, e)
        throw new BusinessException('error.account-not-created', 'Account not created')
    }
}

createAccount()

def resetPassword = {
    String url = uaaApiEndpoint + '/api/account/reset_password/init'
    RequestEntity req = RequestEntity.post(URI.create(url)).header(HttpHeaders.AUTHORIZATION, authorization).body(email)
    try {
        restTemplate.exchange(req, Map.class)
        log.info('SUCCESS: Successfully password reset initiated {}', email)
    } catch (RestClientException e) {
        log.error('FAILURE: Could not reset password: {}. Exception: {}', email, e)
        throw new BusinessException('error.reset-password', 'Password reset not initiated')
    }
}

resetPassword()

return ["status": "success"]