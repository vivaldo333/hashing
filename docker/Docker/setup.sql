CREATE DATABASE IF NOT EXISTS KS;

DROP TABLE IF EXISTS KS.PHONES;

CREATE TABLE IF NOT EXISTS KS.PHONES (
    PHONE VARCHAR(12) NOT NULL,
    PRIMARY KEY (PHONE)
);

INSERT INTO KS.PHONES (PHONE) VALUES ('380000000000');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000001');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000002');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000003');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000004');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000005');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000006');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000007');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000008');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000009');
INSERT INTO KS.PHONES (PHONE) VALUES ('380000000010');