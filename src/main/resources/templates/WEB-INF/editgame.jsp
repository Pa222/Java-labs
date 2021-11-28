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
    <h1>Game:</h1>
    <form th:action="@{/editgame}" th:object="${Game}" method="POST">
        <div class="form__block">
            <label for="title">Title</label>
            <input class="form__input" id="title" name="title" type="text" th:value="*{title}">
        </div>
        <select name="publisher">
            <option th:each="publisher: ${Publishers}" th:value="${publisher.publisherName}">[[${publisher.publisherName}]]</option>
        </select>
        <div class="form__block">
            <label for="price">Price</label>
            <input class="form__input" id="price" name="price" type="number" min="0" max="100" th:value="*{price}">
        </div>
        <div class="form__block">
            <label for="rating">Rating</label>
            <input class="form__input" id="rating" name="rating" type="text" maxlength="3" th:value="*{rating}">
        </div>
        <div class="form__block">
            <label for="description">Description</label>
            <textarea
                    class="form__textarea"
                    id="description"
                    name="gameDescription"
                    cols="60"
                    rows="10"
                    maxlength="4000"
                    th:utext="*{gameDescription}"
            ></textarea>
        </div>
        <input type="hidden" name="id" th:value="*{id}">
        <input class ="button-main-page" type="submit" value="Save" />
    </form>
</div>
<div th:if="${errorMessage}" th:utext="${errorMessage}"
     style="color:red;font-style:italic;">
</div>
</body>