<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/registerForm.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/header.css}"/>
</head>
<body>
<div class="header">
    <a class="header__link" th:href="@{/catalog}">Catalog</a>
</div>
    <div class="wrapper">
        <div class="form-container">
            <form th:action="@{/registration}" th:object="${User}" th:method="POST">
                <div class="form">
                    <input class="form__input" type="text" name="name" placeholder="Name">
                    <input class="form__input" type="text" name="login" placeholder="Log in">
                    <input class="form__input" type="password" name="password" placeholder="Password">
                    <input class="form__input" type="password" name="repeatPassword" placeholder="Repeat password">
                    <input class="form__input" type="submit" value="Register">
                </div>
            </form>
        </div>
        <div th:if="${errorMessage}" th:utext="${errorMessage}"
             style="color:red;font-style:italic; text-align: center">
        </div>
    </div>

</body>
</html>