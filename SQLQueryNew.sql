ALTER TABLE games ADD CONSTRAINT ratingCHK check(rating like '%+');

INSERT INTO Publishers (publisher_name) values ('Valve'), ('Behavior'), ('Mojang');

INSERT INTO Games (publisher_id, title, rating, price, game_description) values (1, 'Dota 2', '17+', 0, 'Самая популярная игра в Steam
Ежедневно миллионы игроков по всему миру сражаются от лица одного из более сотни героев Dota 2, и даже после тысячи часов в ней есть чему научиться. Благодаря регулярным обновлениям игра живёт своей жизнью: геймплей, возможности и герои постоянно преображаются.

Одно поле боя, неиссякаемые возможности
Игра может похвастаться бесконечным разнообразием героев, способностей и предметов, благодаря чему каждый матч уникален. Любой герой может исполнять множество ролей, и для каждой нужды найдутся подходящие предметы. Здесь вас ничто не ограничивает — вы сами выбираете, какой стратегии следовать.

Все герои бесплатны
Равные соревновательные возможности — главная гордость игры. Чтобы все находились в одинаковом положении, её основа — в частности, обширный список героев — доступна каждому игроку. Если хотите, вы можете коллекционировать предметы, видоизменяющие как героев, так и мир вокруг них, но всё необходимое для вашего первого матча уже есть в игре.

Зовите друзей и играйте вместе
Dota 2 многогранна, и она не стоит на месте, но вступить в игру никогда не поздно.
Научитесь основам в матче против ботов. Опробуйте возможности героев. А затем вступайте в битву с игроками вашего уровня и поведения: об этом позаботится система подбора игры.'),
												(2, 'Dead By Daylight', '18+', 12.59, 'Смерть — это не выход

Dead by Daylight - это многопользовательская игра в жанре ужасов в режиме (4 против 1), где один игрок берет на себя роль жестокого Убийцы, а четыре других игрока являются Выжившими, пытающимися сбежать от убийцы.

Выжившие играют от третьего лица и имеют преимущество в ситуационной осведомленности. Убийца играет от первого лица и больше сосредоточен на своей добыче.

Цель Выживших в каждой игре - постараться, чтобы Убийца не поймал, и сбежать с места убийств – это звучит проще, чем на самом деле, особенно когда обстановка меняется в каждой игре.'),
												(1, 'Couter Strike: Global Offensive', '18+', 0, 'Counter-Strike: Global Offensive (CS:GO) расширяет границы ураганной командной игры, представленной ещё 19 лет назад.

CS:GO включает в себя новые карты, персонажей, оружие и режимы игры, а также улучшает классическую составляющую CS (de_dust2 и т. п.).

«Counter-Strike удивила всю игровую индустрию, когда ничем не примечательная модификация стала одним из самых популярных шутеров в мире почти сразу после выпуска в августе 1999 года, — говорит Даг Ломбарди из Valve. — Уже на протяжении 12 лет она продолжает быть одной из самых популярных игр в мире и возглавляет киберспортивные соревнования, а по всему миру продано более 25 миллионов игр этой серии. CS:GO обещает расширить границы заслужившего известность игрового процесса и предложить его игрокам не только на ПК, но и на консолях следующего поколения и компьютерах Mac».'),
												(3, 'Minecraft', '7+', 23.95, 'Minecraft (рус. Майнкрáфт; от англ. mine — «шахта; добывать» + craft — «ремесло») — компьютерная инди-игра в жанре песочницы, созданная шведским программистом Маркусом Перссоном и выпущенная его компанией Mojang AB. Перссон опубликовал начальную версию игры в 2009 году; в конце 2011 года была выпущена стабильная версия для ПК Microsoft Windows, распространявшаяся через официальный сайт. В последующие годы Minecraft была портирована на Linux и macOS для персональных компьютеров; на Android, iOS и Windows Phone для мобильных устройств; на игровые приставки PlayStation 4, Vita, VR, Xbox One, Nintendo 3DS, Switch и Wii U. В 2014 году корпорация Microsoft приобрела права на Minecraft вместе с компанией Mojang AB за 2,5 миллиарда $. Студия 4J портировала игру на игровые приставки, а Xbox Game Studios разработала мультиплатформенную версию Minecraft и специальное издание игры для образовательных учреждений');

declare @cnt int = 0;

while @cnt < 99900
	begin
		INSERT INTO games (publisher_id, title, rating, price, game_description) values (1, concat('Test ', @cnt), '18+', 99, 'Description');
		set @cnt = @cnt + 1;
	end;


SELECT * FROM publishers;
SELECT * FROM games;
SELECT * FROM user_order;
SELECT * FROM order_games;
SELECT * FROM users;

------------------------------------------------------------------------------------------
-- PROCEDURES
------------------------------------------------------------------------------------------

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