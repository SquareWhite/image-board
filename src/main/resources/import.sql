INSERT INTO theme (name) VALUES ('Music');
INSERT INTO theme (name) VALUES ('Programming');
INSERT INTO theme (name) VALUES ('Games');
INSERT INTO theme (name) VALUES ('Something else');
INSERT INTO theme (name) VALUES ('My imagination is quite lacking');

INSERT INTO thread (closed, date_updated, name, theme_themeId) VALUES (FALSE, NOW(), 'Jazz lovers', 1);
INSERT INTO thread (closed, date_updated, name, theme_themeId) VALUES (FALSE, NOW(), 'Rock listeners', 1);
INSERT INTO thread (closed, date_updated, name, theme_themeId) VALUES (FALSE, NOW(), 'Trip hop', 1);
INSERT INTO thread (closed, date_updated, name, theme_themeId) VALUES (FALSE, NOW(), 'Bebop', 1);
INSERT INTO thread (closed, date_updated, name, theme_themeId) VALUES (FALSE, NOW(), 'Its time to stop', 1);

INSERT INTO message (content, date, image, thread_threadId) VALUES ('WOW, JAZZ IS SO COOL!', NOW(), 'image-url', 1);
INSERT INTO message (content, date, image, thread_threadId) VALUES ('I KNOW, RIGHT!', NOW(), 'image-url', 1);
INSERT INTO message (content, date, image, thread_threadId) VALUES ('YOU GUYS ARE LOSERS, JAZZ IS TOTALLY LAME!', NOW(), 'image-url', 1);