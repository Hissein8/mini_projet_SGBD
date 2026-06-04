CREATE USER 'rh_manager'@'localhost' IDENTIFIED BY 'motdepasse123';
GRANT ALL PRIVILEGES ON rh_entreprise.* TO 'rh_manager'@'localhost';

CREATE USER 'employe_role'@'localhost' IDENTIFIED BY 'motdepasse456';
GRANT SELECT ON rh_entreprise.Conge TO 'employe_role'@'localhost';
GRANT SELECT ON rh_entreprise.Bulletin TO 'employe_role'@'localhost';

FLUSH PRIVILEGES;