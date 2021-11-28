DROP PROCEDURE GetUserByLogin;
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

------------------------------------------------------------------------------------------
-- Users
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