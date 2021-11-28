<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" />
    <title>Add Person</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/styles.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/deletegame.css}"/>
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
    <h1>List of games:</h1>
    <form th:action="@{/editgames}" th:object="${filters}" method="GET">
        <div>
            <label>Номер страницы: </label>
            <select class="catalog__pages" onchange="this.form.submit()" th:field="*{pageNumber}">
                <option th:each="i: ${#numbers.sequence(1, pagesAmount)}" th:value="${i}">[[${i}]]</option>
            </select>
        </div>
    </form>
    <table th:each="game : ${games}">
        <tr>
            <td class="table__col" th:utext="${game.id}"></td>
            <td class="table__col" th:utext="${game.title}"></td>
            <td class="table__col">
                <form th:action="@{/deletegame}" th:object="${Game}" method="POST">
                    <input type="hidden" name="id" th:value="${game.id}">
                    <input type="submit" value="Удалить">
                </form>
            </td>
            <td class="table__col">
                <form th:action="@{/editgame}" th:object="${Game}" method="GET">
                    <input type="hidden" name="id" th:value="${game.id}">
                    <input type="submit" value="Редактировать">
                </form>
            </td>
        </tr>
    </table>
</div>
<div th:if="${message}" th:utext="${message}"
     style="color:red;font-style:italic; text-align: center">
</div>
</body>