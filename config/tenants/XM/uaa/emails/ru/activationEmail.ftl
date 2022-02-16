<!DOCTYPE html>
<html>
    <head>
        <title>XM^online активация</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="${baseUrl}/favicon.ico" />
    </head>
    <body>
        <p>
            Дорогой ${user.firstName + ' ' + user.lastName}
        </p>
        <p>
            Ваш XM^online аккаунт был создан, пожалуйста, нажмите на ссылку ниже для его активации:
        </p>
        <p>
            <a href="${baseUrl}/activate?key=${user.activationKey}"
            >Activation Link</a>
        </p>
        <p>
            <span>С уважением, </span>
            <br/>
            <em>XM^online.</em>
        </p>
    </body>
</html>
