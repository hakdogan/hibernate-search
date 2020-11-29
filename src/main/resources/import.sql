INSERT INTO event(id, name) VALUES (1, 'Event Storming vs Event Modeling');
INSERT INTO event(id, name) VALUES (2, 'CI & CD with Azure Devops');
INSERT INTO event(id, name) VALUES (3, 'Introduction to Quarkus');
INSERT INTO event(id, name) VALUES (4, 'Change Data Capture with Debezium and Apache Kafka');

INSERT INTO host(id, name, title, event_id) VALUES (nextval('hibernate_sequence'), 'Rafal Maciag', 'CQRS/ES philosophy advocate', 1);
INSERT INTO host(id, name, title, event_id) VALUES (nextval('hibernate_sequence'), 'Huseyin Akdogan', 'Expert Software Consultant', 2);
INSERT INTO host(id, name, title, event_id) VALUES (nextval('hibernate_sequence'), 'Huseyin Akdogan', 'Expert Software Consultant', 3);
INSERT INTO host(id, name, title, event_id) VALUES (nextval('hibernate_sequence'), 'Aykut Bulgu', 'System Craftsman', 4);