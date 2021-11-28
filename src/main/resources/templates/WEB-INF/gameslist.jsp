<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Games List</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/header.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/catalog.css}"/>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <a class="header__link" th:href="@{/catalog}">Catalog</a>
        <a class="header__link" th:href="@{/login}">Log in</a>
        <a class="header__link" th:href="@{/basket}">Basket</a>
        <a class="header__link" th:href="@{/addgame}">Add Game</a>
        <a class="header__link" th:href="@{/editgames}">Edit Games</a>
        <a class="header__link" th:href="@{/addpublisher}">Add Publisher</a>
        <a class="header__link" th:href="@{/editpublishers}">Edit Publishers</a>
        <a class="header__link" th:href="@{/logout}">Log out</a>
    </div>
    <div class="catalog">
        <form th:action="@{/catalog}" th:object="${filters}" method="GET">
            <div class="catalog__filters">
                <input class="catalog__search-box" type="text" name="searchBox" th:value="*{searchBox}"
                placeholder="Enter name">
                <input class="catalog__button" type="submit" name="search-button" value="Искать">
                <div class="catalog__price-range">
                    <p>Стоимость: </p>
                    <input onchange="this.form.submit()" class="catalog__price-input" type="number" name="priceFrom" th:value="*{priceFrom}"
                    min="0" value="0">
                    <label> до </label>
                    <input onchange="this.form.submit()" class="catalog__price-input" type="number" name="priceTo" th:value="*{priceTo}"
                    max="100" value="100">
                </div>
                <div class="catalog__rating-checkbox">
                    <input onchange="this.form.submit()" type="checkbox" name="rating18" th:field="*{rating18}" th:checked="*{rating18}">
                    <label>18+</label>
                </div>
                <div class="catalog__sorts">
                    <label>Сортировать по: </label>
                    <select onchange="this.form.submit()" th:field="*{sort}">
                        <option th:value="TitleAsc" selected>Наименование: возрастание</option>
                        <option th:value="TitleDesc">Наименование: убывание</option>
                        <option th:value="priceAsc">Цена: возрастание</option>
                        <option th:value="priceDesc">Цена: убывание</option>
                    </select>
                </div>
                <div>
                    <label>Номер страницы: </label>
                    <select class="catalog__pages" onchange="this.form.submit()" th:field="*{pageNumber}">
                        <option th:each="i: ${#numbers.sequence(1, pagesAmount)}" th:value="${i}">[[${i}]]</option>
                    </select>
                </div>
            </div>
        </form>
        <div class="catalog__items" th:each ="game : ${games}">
            <div class="catalog__item">
                <p class="catalog__title">[[${game.title}]] ([[${game.rating}]])</p>
                <p class="catalog__publisher" th:utext="${game.publisher.publisherName}"></p>
                <p class="catalog__description" th:utext="${game.gameDescription}"></p>
                <label class="catalog__price">Стоимость: [[${game.price}]]$</label>
                <input class="catalog__basket-button" type="button" value="В корзину">
            </div>
        </div>
</div>
</body>
</html>