



























/*
INSERT INTO location (location_id, street, number, city, postal_code, country, extra_info)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'), 'Nationalestraat', 50, 'Antwerp', '2000', 'Belgium', 'Vergaderzaal');

INSERT INTO location (location_id, street, number, city, postal_code, country, extra_info)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f2'), 'Della Faillelaan', 34, 'Aartselaar', '2630', 'Belgium', 'Cultureel centrum');

INSERT INTO conference (conference_id, name, description, start_date, end_date, location_location_id, active)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'), 'HAXX 2024','This conference marks the annual gathering of technology enthusiasts and industry leaders, providing a platform for innovative ideas, discussions, and collaborations. Join us as we explore the latest trends and advancements in technology.', '2024-01-13T12:00:00.000', '2024-01-13T20:00:00.000', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'), false);

INSERT INTO users (user_id, first_name, last_name, office_function, profile_picture)
VALUES (UUID_TO_BIN('1495da5e-e394-474d-849c-2ba8636efa57'), 'Mark', 'Kast', 'Senior Dev', null);

INSERT INTO conference (conference_id, name, description, start_date, end_date, location_location_id, active)
VALUES (UUID_TO_BIN('b51b10de-3e8f-4b28-b0c2-3a4941e1a5b5'), 'HAXX 2025',
        'The HAXX 2025 conference aims to continue the tradition of fostering innovation and collaboration within the technology community. Join us for a day filled with insightful discussions, workshops, and networking opportunities.',
        '2025-01-13T12:00:00.000', '2025-01-13T20:00:00.000', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f2'),
        false);


INSERT INTO room (room_id, name, conference_id)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'), 'Main Hall',
        UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO room (room_id, name, conference_id)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f2'), 'The Nether',
        UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO room (room_id, name, conference_id)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f3'), 'The End',
        UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO room (room_id, name, conference_id)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f4'), 'The Village',
        UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO talk(talk_id, description, end_date, start_date, title, room_id)
VALUES (UUID_TO_BIN('3b241101-e2bb-4255-8caf-4136c566a964'), 'This talk will cover the latest advancements in AI and machine learning, and how they are shaping the future of technology.', '2024-01-13T13:00:00.000', '2024-01-13T12:00:00.000', 'AI and Machine Learning', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO talk(talk_id, description, end_date, start_date, title, room_id)
VALUES (UUID_TO_BIN('16fd2706-8baf-433b-82eb-8c7fada847da'), 'What are the new features added in JAVA 21?', '2024-01-13T15:00:00.000', '2024-01-13T13:30:00.000', 'New features of JAVA', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'));

INSERT INTO talk(talk_id, description, end_date, start_date, title, room_id)
VALUES (UUID_TO_BIN('16396b61-4601-4a39-b28f-c823e4c3e7ec'), 'How to do API calls', '2024-01-13T16:30:00.000',
        '2024-01-13T15:30:00.000', 'API basics', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f3'));

INSERT INTO talk(talk_id, description, end_date, start_date, title, room_id)
VALUES (UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f4'), 'How to use the latest version of Angular', '2024-01-13T10:00:00.000',
        '2024-01-13T08:45:00.000', 'Angular 17', UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7f4'));

INSERT INTO feedback(feedback_id, comment, rating, talk_talk_id, user_user_id)
VALUES (UUID_TO_BIN('44b7b3d3-54c1-49a0-b51e-4e2a8b65801b'), 'Great talk!', 5, UUID_TO_BIN('3b241101-e2bb-4255-8caf-4136c566a964'), UUID_TO_BIN('1495da5e-e394-474d-849c-2ba8636efa57'));

INSERT INTO speaker (speaker_id, user_id, bio, phone)
VALUES (UUID_TO_BIN('79252691-744b-4aeb-8cac-f004c360b6b3'), UUID_TO_BIN('1495da5e-e394-474d-849c-2ba8636efa57'),
        'Mark Kast is a senior developer with a passion for technology and innovation. He has been a speaker at various technology conferences and has a wealth of experience in the industry.',
        '0487 12 34 56');

INSERT INTO talk_speaker (talk_id, speaker_id)
VALUES (UUID_TO_BIN('16fd2706-8baf-433b-82eb-8c7fada847da'), UUID_TO_BIN('79252691-744b-4aeb-8cac-f004c360b6b3'));


INSERT INTO message (message_id, subject, message, important, speaker_id, conference_id, for_organisation)
VALUES (UUID_TO_BIN('46e78782-04c0-459f-8d7f-402f13ea04ad'), 'Important message', 'This is an important message', true, UUID_TO_BIN('79252691-744b-4aeb-8cac-f004c360b6b3'), UUID_TO_BIN('a2b92626-1b9e-4ee5-b724-efa6f9a2e7d1'), true);


 */