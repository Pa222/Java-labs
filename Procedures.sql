DROP PROCEDURE GetUserByLogin;
DROP PROCEDURE GetUserOrderGamesIds;
DROP PROCEDURE GetUserRolesIds;
DROP PROCEDURE GetUserOrdersIds;
DROP PROCEDURE CreateUser;
DROP PROCEDURE DropUser;
DROP PROCEDURE GrantAdminToUser;
DROP PROCEDURE GetLastOrderId;
DROP PROCEDURE CreateOrder;
DROP PROCEDURE AddGameToOrder;
DROP PROCEDURE GetPublishers;
DROP PROCEDURE GetPublishersByPageNumber;
DROP PROCEDURE GetPublisherByName;
DROP PROCEDURE GetPublisherById;
DROP PROCEDURE GetPublishersCount;
DROP PROCEDURE AddPublisher;
DROP PROCEDURE DeletePublisher;
DROP PROCEDURE EditPublisher;
DROP PROCEDURE GetGames;
DROP PROCEDURE GetGamesCount;
DROP PROCEDURE GetGamesByTitleCount;
DROP PROCEDURE GetGamesByPageNumber;
DROP PROCEDURE GetGameById;
DROP PROCEDURE DeleteGame;
DROP PROCEDURE AddGame;
DROP PROCEDURE UpdateGameInfo;

------------------------------------------------------------------------------------------
-- Users
------------------------------------------------------------------------------------------

go
CREATE PROCEDURE GetUserByLogin @login varchar(255) as
	SELECT * FROM users WHERE login = @login;
go

go
CREATE PROCEDURE GetUserOrderGamesIds @order_id int, @user_id int as
	SELECT g.id as id
		FROM user_order uo 
			INNER JOIN order_games og ON uo.id = og.order_id
			INNER JOIN games g ON g.id = og.game_id
		WHERE uo.user_id = @user_id AND uo.id = @order_id;
go

go
CREATE PROCEDURE GetUserRolesIds @id int as
	SELECT role_id FROM users u 
		INNER JOIN users_roles ur ON u.id = ur.user_id
		INNER JOIN roles r ON r.id = ur.role_id
		WHERE u.id = @id;
go

go
CREATE PROCEDURE GetUserOrdersIds @id int as 
	SELECT id from user_order WHERE user_id = @id;
go

go
CREATE PROCEDURE CreateUser @login varchar(255), @password varchar(255), @salt varbinary(255), @name varchar(255) as
	INSERT INTO users (login, name, password, salt) values (@login, @name, @password, @salt);
	declare @user_id int;
	declare @role_id int;
	set @user_id = (SELECT id FROM users WHERE login = @login);
	set @role_id = (SELECT id FROM roles WHERE name = 'user');
	INSERT INTO users_roles (user_id, role_id) values (@user_id, @role_id);
go

go
CREATE PROCEDURE DropUser @id int as
	DELETE FROM users_roles WHERE user_id = @id;
	declare @order_id int;
	set @order_id = (SELECT id FROM user_order WHERE user_id = @id);
	DELETE FROM order_games WHERE order_id = @order_id;
	DELETE FROM user_order WHERE user_id = @id;
	DELETE FROM users WHERE id = @id;
go

go
CREATE PROCEDURE GrantAdminToUser @id int as
	declare @role_id int;
	set @role_id = (SELECT id FROM roles WHERE name = 'admin');
	INSERT INTO users_roles (user_id, role_id) values (@id, @role_id);
go

delete from order_games;
delete from user_order;

select * from user_order;
select * from order_games;

------------------------------------------------------------------------------------------
-- Users
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
-- Order
------------------------------------------------------------------------------------------

go
CREATE PROCEDURE GetLastOrderId @user_id int as
	SELECT TOP 1 id FROM user_order WHERE user_id = @user_id ORDER BY id desc;
go

go
CREATE PROCEDURE CreateOrder @user_id int, @total float as
	INSERT INTO user_order (user_id, total_amount) values (@user_id, @total);
go

go
CREATE PROCEDURE AddGameToOrder @order_id int, @game_id int as
	INSERT INTO order_games (game_id, order_id) values (@game_id, @order_id);
go

------------------------------------------------------------------------------------------
-- Order
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
-- Publishers
------------------------------------------------------------------------------------------

go
CREATE PROCEDURE GetPublishers as
	SELECT * FROM publishers;
go

go
CREATE PROCEDURE GetPublishersByPageNumber @page_number int, @page_size int as
	declare @start_point int = 0;
	declare @end_point int = 0;
	set @start_point = ((@page_number - 1) * @page_size) + 1;
	set @end_point = @start_point + @page_size - 1;
	SELECT *
	FROM (SELECT ROW_NUMBER() OVER(ORDER BY (select NULL as noorder)) AS RowNum, *
			FROM publishers) as alias
	WHERE RowNum BETWEEN @start_point AND @end_point;
go

go
CREATE PROCEDURE GetPublisherByName @name varchar(255) as
	SELECT * FROM publishers WHERE publisher_name = @name;
go

go
CREATE PROCEDURE GetPublisherById @id int as
	SELECT * FROM publishers WHERE id = @id;
go

go
CREATE PROCEDURE GetPublishersCount as
	SELECT COUNT(*) FROM publishers;
go

go
CREATE PROCEDURE AddPublisher @name varchar(255) as
	INSERT INTO publishers (publisher_name) values (@name);
go

go
CREATE PROCEDURE DeletePublisher @id int as
	DELETE FROM publishers WHERE id = @id;
go

go
CREATE PROCEDURE EditPublisher @id int, @name varchar(255) as
	UPDATE publishers set publisher_name = @name WHERE id = @id;
go

------------------------------------------------------------------------------------------
-- Publishers
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
-- Games
------------------------------------------------------------------------------------------

go
CREATE PROCEDURE GetGames as
	SELECT * FROM games;
go

go
CREATE PROCEDURE GetGamesCount as
	SELECT count(*) FROM games;
go

go
CREATE PROCEDURE GetGamesByTitleCount @title varchar(255) as
	SELECT count(*) FROM games WHERE title like '%' + @title + '%';
go

go
CREATE PROCEDURE GetGameById @id int as
	SELECT * FROM games where id = @id;
go

go
CREATE PROCEDURE GetGamesByPageNumber @page_number int, @page_size int, @title varchar(255) = null as
	declare @start_point int = 0;
	declare @end_point int = 0;
	set @start_point = ((@page_number - 1) * @page_size) + 1;
	set @end_point = @start_point + @page_size - 1;
	if (@title is null)
		SELECT *
		FROM (SELECT ROW_NUMBER() OVER(ORDER BY (select NULL as noorder)) AS RowNum, *
			  FROM games) as alias
		WHERE RowNum BETWEEN @start_point AND @end_point;
	else
		SELECT *
		FROM (SELECT ROW_NUMBER() OVER(ORDER BY (select NULL as noorder)) AS RowNum, *
			  FROM games) as alias
		WHERE RowNum BETWEEN @start_point AND @end_point AND title like '%' + @title + '%';
go

go
CREATE PROCEDURE DeleteGame @gameId int as 
begin
	DELETE FROM order_games WHERE game_id = @gameId;
	DELETE FROM games WHERE id = @gameId;
end;
go

go
CREATE PROCEDURE AddGame @publisher_id int, @title varchar(255), @rating varchar(3), @price float, @game_description varchar(4000) as
begin
	INSERT INTO games (publisher_id, title, rating, price, game_description) values (@publisher_id, @title, @rating, @price, @game_description);
end;
go

go
CREATE PROCEDURE UpdateGameInfo @id int, @publisher_id int, @title varchar(255), @rating varchar(3), @price float, @game_description varchar(4000) as
begin
	UPDATE games SET publisher_id = @publisher_id, title = @title, rating = @rating, price = @price, game_description = @game_description where id = @id;
end;
go

------------------------------------------------------------------------------------------
-- Games
------------------------------------------------------------------------------------------