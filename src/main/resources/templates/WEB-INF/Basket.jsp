<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Add Person</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/header.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/basket.css}"/>
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
    <h1>Basket</h1>
    <div class="container">
    </div>
</div>
</body>