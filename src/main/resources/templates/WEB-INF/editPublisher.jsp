<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Add Person</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/addgame.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/header.css}"/>
</head>
<body>
<div class="header">
    <a class="header__link" th:href="@{/catalog}">Catalog</a>
    <a class="header__link" th:href="@{/basket}">Basket</a>
    <a class="header__link" th:href="@{/addgame}">Add Game</a>
    <a class="header__link" th:href="@{/editgames}">Edit Games</a>
    <a class="header__link" th:href="@{/addpublisher}">Add Publisher</a>
    <a class="header__link" th:href="@{/editpublishers}">Edit Publishers</a>
    <a class="header__link" th:href="@{/logout}">Log out</a>
</div>
<div class="container" th:align="center">
    <h1>Publisher:</h1>
    <form th:action="@{/editpublisher}" th:object="${Publisher}" method="POST">
        <div class="form__block">
            <label for="publisherName">Publisher Name:</label>
            <input class="form__input" id="publisherName" name="publisherName" type="text" th:value="*{publisherName}">
            <input type="hidden" name="id" th:value="*{id}">
        </div>
        <input class ="button-main-page" type="submit" value="Edit" />
    </form>
</div>
<div th:if="${errorMessage}" th:utext="${errorMessage}"
     style="color:red;font-style:italic;">
</div>
</body>