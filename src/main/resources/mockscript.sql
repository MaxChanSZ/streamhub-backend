DESCRIBE account;
DESCRIBE series;
DESCRIBE video;

SELECT * FROM series;
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1000, 'Walt Disney', 'free copyright movie', '45', '19280101', 'Steamboat Willie', 'steamboatwillie_001.jpg', 'Comedy');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1001, 'Private Snafu', 'free copyright movie snafu', '46', '19430101', 'Private Snafu', 'privatesnafu_001.jpg', 'Documentary');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1002, 'Douglas Fairbanks, Noah Berry', 'action movie', '42', '19250101', 'The Mark of Zorro', 'themarkofzorro_001.jpg', 'Adventure');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1003, 'Charles Laughton', 'pirate movie', '41', '19450101', 'Captain Kidd', 'captainkidd_001.jpg', 'Adventure');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1004, 'Walt Disney', 'cartoon movie', '49', '19230101', 'Alice Wonderland', 'alicewonderland_001.jpg', 'Documentary');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1005, 'Pat Sullivan', 'cartoon cat movie', '43', '19190101', 'Feline Follies', 'felinefollies_001.jpg', 'Drama');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1006, 'Max Fleischer', 'cartoon movie', '44', '19360101', 'Popeye The Sailor Meets Sindbad The Sailor', 'popeyethesailor_001.jpg', 'Comedy');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1007, 'Frank Tashlin', 'cartoon movie', '43', '19430101', 'Puss and Booty', 'pussandbooty_001.jpg', 'Comedy');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1008, 'Sidney Toler', 'thriller movie', '46', '19460109', 'The Trap', 'thetrap_001.jpg', 'Drama');
INSERT INTO series (id, cast, description, rating, release_date, series_title, thumbnailurl, category) VALUES (1009, 'Frank Sinatra', 'detective movie', '42', '19540101', 'Suddenly', 'suddenly_001.jpg', 'Thriller');

SELECT * FROM video;
INSERT INTO video (id, description, duration_second, episode, release_date, thumbnailurl, video_title, videourl, series_id) VALUES (1, 'first episode of steamboat willie', 460, 1, '20200101', 'steamboatwillie_001.jpg', 'steamboat willie first episode', 'steamboatwillie_001.m3u8', 1000);