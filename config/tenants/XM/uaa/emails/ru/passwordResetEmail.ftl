<!DOCTYPE html>
<html>
    <head>
        <title>XM^online cброс пароля</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="shortcut icon" href="${baseUrl}/favicon.ico" />
    </head>
    <body>
        <p>
            Дорогой ${user.firstName}
        </p>
        <p>
            Для вашего XM^online аккаунта был запрошен cброс пароля, пожалуйста, нажмите на ссылку ниже для его cброса:
        </p>
        <p>
            <a href="${baseUrl}/reset/finish?key=${user.resetKey}">Reset Link</a>
        </p>
        <p>
            <span>С уважением, </span>
            <br/>
            <em>XM^online.</em>
        </p>
    </body>
</html>
