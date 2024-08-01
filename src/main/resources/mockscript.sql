DESCRIBE account;
DESCRIBE series;
DESCRIBE video;

SELECT * FROM account;
INSERT INTO account (id, email, password, username) VALUES (1000, 'admin@mail.com', 'admin001', 'admin001');
INSERT INTO account (id, email, password, username) VALUES (1001, 'johndoe@gmail.com', 'john123', 'johndoe');
INSERT INTO account (id, email, password, username) VALUES (1002, 'janedoe@gmail.com', 'jane123', 'janedoe');
INSERT INTO account (id, email, password, username) VALUES (1003, 'chrisdoe@gmail.com', 'chris123', 'chrisdoe');

SELECT * FROM series;
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl) VALUES (1000, 'Walt Disney', 'free copyright movie', '4.5/5.0', '20200101', 'Steamboat Willie', 'steamboatwillie_001.jpg');

SELECT * FROM video;
INSERT INTO video (id, description, duration_second, episode, release_date, thumbnailurl, video_title, videourl, series_id) VALUES (1000, 'first episode of steamboat willie', 460, 1, '20200101', 'steamboatwillie_001.jpg', 'steamboat willie first episode', 'steamboatwillie_001.m3u8', 1000);