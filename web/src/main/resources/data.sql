INSERT INTO amenity (id, name) VALUES
                                   (11, 'Projector'),
                                   (12, 'WiFi'),
                                   (13, 'Whiteboard'),
                                   (14, 'Air Conditioning'),
                                   (15, 'Sound System'),
                                   (16, 'Mini Fridge'),
                                   (17, 'Tool Kit'),
                                   (18, 'Camera Setup'),
                                   (19, 'Lighting Equipment'),
                                   (20, 'Research Equipment');

INSERT INTO location (id, city, building, lat, lng, street) VALUES
                                                                (100, 'Timișoara', 'Iulius Mall', 45.76637896036854, 21.227258291549923, 'Piața Consiliul Europei'),
                                                                (101, 'Timișoara', 'Bloc 4', 45.76529177818932, 21.222693443560274, 'Strada Brândușei'),
                                                                (102, 'Timișoara', '', 45.74690936100504 , 21.22686910635821, 'Bulevardul Vasile Pârvan 2');

INSERT INTO room (id, name, location_id, capacity) VALUES
                                                       (10, 'Sala de conferințe A', 100, 50),
                                                       (11, 'Sala de ședințe B', 101, 20),
                                                       (12, 'Sala de curs C', 102, 30),
                                                       (13, 'Sala IT D', 100, 25),
                                                       (14, 'Sala de training E', 101, 40),
                                                       (15, 'Sala multimedia F', 102, 35),
                                                       (16, 'Laborator G', 100, 15),
                                                       (17, 'Camera de brainstorming H', 101, 10),
                                                       (18, 'Sala open-space I', 102, 60),
                                                       (19, 'Birou directorial J', 100, 5),
                                                       (20, 'Sala de lucru K', 101, 12),
                                                       (21, 'Sala de seminarii L', 102, 45),
                                                       (22, 'Sala de interviuri M', 100, 8),
                                                       (23, 'Sala de curs N', 101, 30),
                                                       (24, 'Atelier O', 102, 18),
                                                       (25, 'Sala tehnică P', 100, 20),
                                                       (26, 'Sala de prezentări Q', 101, 55),
                                                       (27, 'Studio video R', 102, 10),
                                                       (28, 'Cabinet S', 100, 6),
                                                       (29, 'Sala de cercetare T', 101, 14);


INSERT INTO room_amenities (room_id, amenity_id) VALUES
                                                     (10, 11), (10, 12),
                                                     (11, 13),
                                                     (12, 14), (12, 12),
                                                     (13, 11), (13, 13),
                                                     (14, 12), (14, 15),
                                                     (15, 14), (15, 11),
                                                     (16, 13),
                                                     (17, 12),
                                                     (18, 14), (18, 11), (18, 15),
                                                     (19, 16),
                                                     (20, 12), (20, 13),
                                                     (21, 11),
                                                     (22, 13), (22, 12),
                                                     (23, 14), (23, 11),
                                                     (24, 17), (24, 12),
                                                     (25, 12), (25, 13),
                                                     (26, 11), (26, 15),
                                                     (27, 18), (27, 19),
                                                     (28, 12),
                                                     (29, 20), (29, 14);

INSERT INTO named_group (id, name, size) VALUES
                                             (20, 'Grupa A', 10),
                                             (21, 'Grupa B', 15),
                                             (23, 'Grupa C', 12);
