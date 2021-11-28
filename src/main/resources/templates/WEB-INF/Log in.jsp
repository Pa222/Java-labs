<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/loginForm.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/header.css}"/>
</head>
<body>
<div class="header">
    <a class="header__link" th:href="@{/catalog}">Catalog</a>
</div>
    <div class="wrapper">
        <div class="form-container">
            <form th:action="@{/login}" th:object="${User}" th:method="POST">
                <div class="form">
                    <input class="form__input" type="text" name="username" placeholder="Log in">
                    <input class="form__input" type="password" name="password" placeholder="Password">
                    <input class="form__input" type="submit" value="Log In">
                </div>
                <a class="form__link" th:href="@{/registration}">No account? Register</a>
            </form>
        </div>
        <div th:if="${param.error}" style="color:red;font-style:italic; text-align: center">
            Invalid username and password.
        </div>
    </div>
</body>
</html>