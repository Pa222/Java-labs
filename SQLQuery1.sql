use Java;

CREATE TABLE Publishers(
	id int identity(0, 1) primary key,
	publisher_name nvarchar(50) not null
);

INSERT INTO Publishers (publisher_name) values ('Valve'), ('Behavior'), ('Mojang');

SELECT * FROM Publishers;

DROP TABLE Games;

CREATE TABLE Games(
	id int identity(0, 1) primary key,
	title nvarchar(50) not null,
	publisher_id int not null,
	rating nvarchar(3) not null default('0+'),
	price decimal(10, 2) not null default(0),
	game_description nvarchar(4000) not null default('Description'),
	constraint rating_CHK check(rating like '%+'),
	foreign key (publisher_id) references Publishers(id),
);

INSERT INTO Games (publisher_id, title, rating, price, game_description) values (0, 'Dota 2', '17+', 0, 'Самая популярная игра в Steam
Ежедневно миллионы игроков по всему миру сражаются от лица одного из более сотни героев Dota 2, и даже после тысячи часов в ней есть чему научиться. Благодаря регулярным обновлениям игра живёт своей жизнью: геймплей, возможности и герои постоянно преображаются.

Одно поле боя, неиссякаемые возможности
Игра может похвастаться бесконечным разнообразием героев, способностей и предметов, благодаря чему каждый матч уникален. Любой герой может исполнять множество ролей, и для каждой нужды найдутся подходящие предметы. Здесь вас ничто не ограничивает — вы сами выбираете, какой стратегии следовать.

Все герои бесплатны
Равные соревновательные возможности — главная гордость игры. Чтобы все находились в одинаковом положении, её основа — в частности, обширный список героев — доступна каждому игроку. Если хотите, вы можете коллекционировать предметы, видоизменяющие как героев, так и мир вокруг них, но всё необходимое для вашего первого матча уже есть в игре.

Зовите друзей и играйте вместе
Dota 2 многогранна, и она не стоит на месте, но вступить в игру никогда не поздно.
Научитесь основам в матче против ботов. Опробуйте возможности героев. А затем вступайте в битву с игроками вашего уровня и поведения: об этом позаботится система подбора игры.'),
												(1, 'Dead By Daylight', '18+', 12.59, 'Смерть — это не выход

Dead by Daylight - это многопользовательская игра в жанре ужасов в режиме (4 против 1), где один игрок берет на себя роль жестокого Убийцы, а четыре других игрока являются Выжившими, пытающимися сбежать от убийцы.

Выжившие играют от третьего лица и имеют преимущество в ситуационной осведомленности. Убийца играет от первого лица и больше сосредоточен на своей добыче.

Цель Выживших в каждой игре - постараться, чтобы Убийца не поймал, и сбежать с места убийств – это звучит проще, чем на самом деле, особенно когда обстановка меняется в каждой игре.'),
												(0, 'Couter Strike: Global Offensive', '18+', 0, 'Counter-Strike: Global Offensive (CS:GO) расширяет границы ураганной командной игры, представленной ещё 19 лет назад.

CS:GO включает в себя новые карты, персонажей, оружие и режимы игры, а также улучшает классическую составляющую CS (de_dust2 и т. п.).

«Counter-Strike удивила всю игровую индустрию, когда ничем не примечательная модификация стала одним из самых популярных шутеров в мире почти сразу после выпуска в августе 1999 года, — говорит Даг Ломбарди из Valve. — Уже на протяжении 12 лет она продолжает быть одной из самых популярных игр в мире и возглавляет киберспортивные соревнования, а по всему миру продано более 25 миллионов игр этой серии. CS:GO обещает расширить границы заслужившего известность игрового процесса и предложить его игрокам не только на ПК, но и на консолях следующего поколения и компьютерах Mac».'),
												(2, 'Minecraft', '7+', 23.95, 'Minecraft (рус. Майнкрáфт; от англ. mine — «шахта; добывать» + craft — «ремесло») — компьютерная инди-игра в жанре песочницы, созданная шведским программистом Маркусом Перссоном и выпущенная его компанией Mojang AB. Перссон опубликовал начальную версию игры в 2009 году; в конце 2011 года была выпущена стабильная версия для ПК Microsoft Windows, распространявшаяся через официальный сайт. В последующие годы Minecraft была портирована на Linux и macOS для персональных компьютеров; на Android, iOS и Windows Phone для мобильных устройств; на игровые приставки PlayStation 4, Vita, VR, Xbox One, Nintendo 3DS, Switch и Wii U. В 2014 году корпорация Microsoft приобрела права на Minecraft вместе с компанией Mojang AB за 2,5 миллиарда $. Студия 4J портировала игру на игровые приставки, а Xbox Game Studios разработала мультиплатформенную версию Minecraft и специальное издание игры для образовательных учреждений');

SELECT * FROM Games;

DROP TABLE Users;

CREATE TABLE Users(
	id int identity(0, 1) primary key,
	login nvarchar(20),
	password nvarchar(20)
);

INSERT INTO Users (login, password) values ('pa2', '11');

SELECT * FROM Users;

DROP TABLE Order_Games;
DROP TABLE User_Order;

CREATE TABLE User_Order(
	id int identity(0, 1) primary key,
	user_id int not null,
	total_amount decimal(10, 2) default(0),
	foreign key (user_id) references Users(id),
);


CREATE TABLE Order_Games(
	id int identity(0, 1) primary key,
	order_id int not null,
	game_id int not null,
	foreign key (order_id) references User_Order(id),
	foreign key (game_id) references Games(id),
);

SELECT * FROM User_Order;
SELECT * FROM Order_Games;

INSERT INTO User_Order (user_id) values (0);
INSERT INTO Order_Games (order_id, game_id) values (0, 1), (0, 0);

------------------------------------------------------------------------------------------
-- PROCEDURES
------------------------------------------------------------------------------------------

DROP PROCEDURE DeleteGame;

go
CREATE PROCEDURE DeleteGame @gameId int as 
begin
	DELETE FROM OrderGames WHERE game_id = @gameId;
	DELETE FROM Games WHERE id = @gameId;
end;
go