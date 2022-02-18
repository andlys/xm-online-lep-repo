package function


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.context.request.RequestContextHolder

// get HTTP request
def httpServletRequest = RequestContextHolder.getRequestAttributes()?.getRequest()
// get body as Map
def body = new ObjectMapper().readValue(httpServletRequest.getContentAsByteArray(), Map.class)
// get header 'Accept-language'
def language = httpServletRequest?.getHeader('accept-language')
// get query string
def query = httpServletRequest?.getQueryString()
// get query parameter that contains the letters [] (example for the characteristic[amount])
def amount = query?.split('&').find{ it.startsWith('characteristic%5Bamount%5D') }?.split('=').last()