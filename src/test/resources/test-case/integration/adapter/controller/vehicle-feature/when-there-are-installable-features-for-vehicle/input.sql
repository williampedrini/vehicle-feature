INSERT INTO hardware (code) VALUES ('6IHTbr');
INSERT INTO hardware (code) VALUES ('6IHTba');
INSERT INTO software (code) VALUES ('GdS6TI');
INSERT INTO software (code) VALUES ('ykzkfK');

INSERT INTO feature (name) VALUES ('FeatureA');
INSERT INTO feature_hardware_included (feature_id, hardware_id) VALUES ((select id from feature where name = 'FeatureA'), (select id from hardware where code = '6IHTba'));
INSERT INTO feature_hardware_excluded (feature_id, hardware_id) VALUES ((select id from feature where name = 'FeatureA'), (select id from hardware where code = '6IHTbr'));
INSERT INTO feature_software_included (feature_id, software_id) VALUES ((select id from feature where name = 'FeatureA'), (select id from software where code = 'GdS6TI'));
INSERT INTO feature_software_excluded (feature_id, software_id) VALUES ((select id from feature where name = 'FeatureA'), (select id from software where code = 'ykzkfK'));

INSERT INTO vehicle (vin) VALUES ('3C3CFFER4ET929645');
INSERT INTO vehicle_software (vehicle_id, software_id) VALUES ((select id from vehicle where vin = '3C3CFFER4ET929645'), (select id from software where code = 'GdS6TI'));
INSERT INTO vehicle_hardware (vehicle_id, hardware_id) VALUES ((select id from vehicle where vin = '3C3CFFER4ET929645'), (select id from hardware where code = '6IHTba'));