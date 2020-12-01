INSERT INTO host(id, firstname, lastname, title) VALUES (1, 'Rafal', 'Maciag', 'CQRS/ES philosophy advocate');
INSERT INTO host(id, firstname, lastname, title) VALUES (2, 'Huseyin', 'Akdogan', 'Expert Software Consultant');
INSERT INTO host(id, firstname, lastname, title) VALUES (3, 'Aykut', 'Bulgu', 'System Craftsman');

INSERT INTO event(id, name, host_id) VALUES (nextval('hibernate_sequence'), 'Event Storming vs Event Modeling', 1);
INSERT INTO event(id, name, host_id) VALUES (nextval('hibernate_sequence'), 'CI & CD with Azure Devops', 2);
INSERT INTO event(id, name, host_id) VALUES (nextval('hibernate_sequence'), 'Introduction to Quarkus', 2);
INSERT INTO event(id, name, host_id) VALUES (nextval('hibernate_sequence'), 'Change Data Capture with Debezium and Apache Kafka', 3);