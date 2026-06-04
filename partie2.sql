-- Création (LDD)
-- Créer la base de données rh_entreprise et toutes ses tables avec les contraintes
-- appropriées.

create database rh_entreprise;
use rh_entreprise;


create table Departement (
    code_dpt char(3),
    nom_dpt varchar(30) not null,
    budget_annuel bigint not null,
    constraint pk_departement primary key (code_dpt)
);

create table Poste (
    code_poste char(5),
    intitule varchar(50) not null,
    salaire_base bigint not null,
    niveau_responsabilite int not null,
    constraint pk_poste primary key (code_poste)
);

create table Employe (
    matricule char(8) not null,
    nom_emp varchar(30) not null,
    prenom_emp varchar(50) not null,
    date_naissance date not null,
    date_embauche date not null,
    email varchar(50) not null,
    telephone varchar(15) not null,
    code_dpt char(3),
    code_poste char(5),
    constraint pk_employe primary key (matricule),
    constraint fk_employe_departement foreign key (code_dpt) references Departement(code_dpt),
    constraint fk_employe_poste foreign key (code_poste) references Poste(code_poste)
);


create table Contrat (
    employe_contrat char(8) not null,
    date_debut_contrat date not null,
    date_fin_contrat date,
    type_contrat varchar(15) not null,
    salaire_brut_contrat bigint not null,
    constraint pk_contrat primary key (employe_contrat, date_debut_contrat),
    constraint ck_contrat check (type_contrat in ('CDI', 'CDD', 'Stage')), 
    constraint fk_contrat_employe foreign key (employe_contrat) references Employe(matricule) 
);


create table Presence ( 
    employe_presence char(8) not null,
    date_presence date not null,
    -- heure_arrivee timestamp default current_timestamp not null,
    heure_arrivee time not null, 
    heure_depart time not null, 
    statut_presence varchar(15),
    constraint pk_presence primary key (employe_presence, date_presence),
    constraint fk_presence_employe foreign key (employe_presence) references Employe(matricule),
    constraint ck_statut_presence check (statut_presence in ('present', 'absent', 'conge')) 
);


create table Conge (
    employe_conge char(8) not null,
    date_debut_conge date not null,
    date_fin_conge date not null,
    type_conge varchar(15) not null,
    statut_conge varchar(15) not null,
    constraint pk_conge primary key (employe_conge, date_debut_conge),
    constraint fk_conge_employe foreign key (employe_conge) references Employe(matricule),
    constraint ck_type_conge check (type_conge in ('annuel', 'maladie', 'maternite')),
    constraint ck_statut_conge check (statut_conge in ('demande', 'approuve', 'refuse'))
);




create table Bulletin (
    employe_bulletin char(8) not null,
    mois_bulletin int not null,
    annee_bulletin int not null,
    salaire_brut bigint not null,
    nb_jours_travailles int not null,
    nb_absences_injustifiees int not null,
    retenues bigint not null, 
    salaire_net bigint not null,
    constraint pk_bulletin primary key (employe_bulletin, mois_bulletin, annee_bulletin),
    constraint fk_bulletin_employe foreign key (employe_bulletin) references Employe(matricule)
);

-- Alimentation (LMD)
-- Insérer au minimum : 4 départements, 8 postes, 20 employés, 20 contrats, 30 présences,
-- 10 congés, 20 bulletins.

INSERT INTO Departement (code_dpt, nom_dpt, budget_annuel) VALUES
('RH', 'Ressources Humaines', 90000000),
('IT', 'Informatique & Digital', 300000000),
('FIN', 'Finance et Comptabilité', 180000000),
('MAR', 'Marketing et Communication', 120000000);


INSERT INTO Poste (code_poste, intitule, salaire_base, niveau_responsabilite) VALUES
('DIR01', 'Directeur de Département', 3500000, 5),
('DEV01', 'Ingénieur Backend Senior', 1800000, 3),
('DEV02', 'Développeur Fullstack Junior', 800000, 1),
('RH01', 'Responsable RH', 1500000, 3),
('COMP1', 'Comptable Senior', 1200000, 2),
('MKT01', 'Community Manager', 500000, 1),
('SYS01', 'Administrateur Systèmes & Réseaux', 1600000, 3),
('ANA01', 'Analyste Financier', 1700000, 3);


INSERT INTO Employe (matricule, nom_emp, prenom_emp, date_naissance, date_embauche, email, telephone, code_dpt, code_poste) VALUES
('EMP00001', 'Ndiaye', 'Cheikh Tidiane', '1985-03-15', '2015-01-10', 'ct.ndiaye@entreprise.sn', '771234567', 'IT', 'DIR01'),
('EMP00002', 'Diop', 'Aminata', '1990-07-22', '2018-05-12', 'a.diop@entreprise.sn', '772345678', 'RH', 'RH01'),
('EMP00003', 'Sall', 'Moustapha', '1993-11-05', '2020-09-01', 'm.sall@entreprise.sn', '773456789', 'IT', 'DEV01'),
('EMP00004', 'Diallo', 'Maimouna', '1995-02-18', '2021-03-15', 'm.diallo@entreprise.sn', '774567890', 'IT', 'DEV02'),
('EMP00005', 'Faye', 'Ousmane', '1988-09-30', '2017-11-01', 'o.faye@entreprise.sn', '775678901', 'FIN', 'COMP1'),
('EMP00006', 'Ba', 'Khadija', '1992-05-14', '2019-02-15', 'k.ba@entreprise.sn', '776789012', 'MAR', 'MKT01'),
('EMP00007', 'Sow', 'Abdoulaye', '1987-12-25', '2016-06-20', 'a.sow@entreprise.sn', '777890123', 'IT', 'SYS01'),
('EMP00008', 'Cissé', 'Fatoumata Binta', '1994-04-02', '2021-07-01', 'fb.cisse@entreprise.sn', '778901234', 'FIN', 'ANA01'),
('EMP00009', 'Traoré', 'Moussa', '1991-08-12', '2020-01-15', 'm.traore@entreprise.sn', '761234567', 'IT', 'DEV01'), -- Profil régional (Mali)
('EMP00010', 'Gaye', 'Awa', '1996-10-10', '2022-02-01', 'a.gaye@entreprise.sn', '762345678', 'MAR', 'MKT01'),
('EMP00011', 'Sy', 'Malick', '1989-01-28', '2018-10-05', 'm.sy@entreprise.sn', '763456789', 'RH', 'RH01'),
('EMP00012', 'Kofi', 'Emmanuel', '1995-06-15', '2021-09-15', 'e.kofi@entreprise.sn', '764567890', 'IT', 'DEV02'), -- Profil régional (Ghana)
('EMP00013', 'Sané', 'Lamine', '1984-11-22', '2014-05-01', 'l.sane@entreprise.sn', '701234567', 'FIN', 'DIR01'),
('EMP00014', 'Mané', 'Astou', '1992-03-09', '2019-11-15', 'a.mane@entreprise.sn', '702345678', 'MAR', 'DIR01'),
('EMP00015', 'Fall', 'Ibrahima', '1997-07-07', '2023-03-01', 'i.fall@entreprise.sn', '703456789', 'IT', 'DEV02'),
('EMP00016', 'Camara', 'Seynabou', '1993-12-12', '2020-06-15', 's.camara@entreprise.sn', '704567890', 'FIN', 'COMP1'),
('EMP00017', 'Thiam', 'Babacar', '1990-04-25', '2018-03-20', 'b.thiam@entreprise.sn', '751234567', 'IT', 'SYS01'),
('EMP00018', 'Goudiaby', 'Mariama', '1996-01-30', '2022-05-10', 'm.goudiaby@entreprise.sn', '752345678', 'MAR', 'MKT01'),
('EMP00019', 'Keita', 'Sekou', '1994-08-19', '2021-11-01', 's.keita@entreprise.sn', '753456789', 'IT', 'DEV01'), -- Profil régional (Guinée)
('EMP00020', 'Seck', 'Yacine', '1991-10-05', '2019-07-15', 'y.seck@entreprise.sn', '754567890', 'RH', 'RH01');


INSERT INTO Contrat (employe_contrat, date_debut_contrat, date_fin_contrat, type_contrat, salaire_brut_contrat) VALUES
('EMP00001', '2015-01-10', NULL, 'CDI', 3800000),
('EMP00002', '2018-05-12', NULL, 'CDI', 1600000),
('EMP00003', '2020-09-01', NULL, 'CDI', 1950000),
('EMP00004', '2021-03-15', '2022-03-14', 'CDD', 850000),
('EMP00005', '2017-11-01', NULL, 'CDI', 1300000),
('EMP00006', '2019-02-15', NULL, 'CDI', 550000),
('EMP00007', '2016-06-20', NULL, 'CDI', 1700000),
('EMP00008', '2021-07-01', NULL, 'CDI', 1850000),
('EMP00009', '2020-01-15', NULL, 'CDI', 1900000),
('EMP00010', '2022-02-01', '2022-08-01', 'Stage', 200000),
('EMP00011', '2018-10-05', NULL, 'CDI', 1550000),
('EMP00012', '2021-09-15', '2022-09-14', 'CDD', 800000),
('EMP00013', '2014-05-01', NULL, 'CDI', 3600000),
('EMP00014', '2019-11-15', NULL, 'CDI', 3500000),
('EMP00015', '2023-03-01', '2023-09-01', 'Stage', 250000),
('EMP00016', '2020-06-15', NULL, 'CDI', 1250000),
('EMP00017', '2018-03-20', NULL, 'CDI', 1650000),
('EMP00018', '2022-05-10', '2023-05-09', 'CDD', 500000),
('EMP00019', '2021-11-01', NULL, 'CDI', 1800000),
('EMP00020', '2019-07-15', NULL, 'CDI', 1500000),
('EMP00004', '2025-06-25', '2026-06-25', 'CDD', 850000), -- Expire dans une vingtaine de jours
('EMP00012', '2025-07-01', '2026-07-01', 'CDD', 800000), -- Expire dans moins de 30 jours
('EMP00018', '2025-06-15', '2026-06-15', 'CDD', 500000); -- Expire dans une dizaine de jours


INSERT INTO Presence (employe_presence, date_presence, heure_arrivee, heure_depart, statut_presence) VALUES
('EMP00001', '2026-05-04', '07:55:00', '17:30:00', 'present'),
('EMP00002', '2026-05-04', '08:15:00', '17:00:00', 'present'),
('EMP00003', '2026-05-04', '08:00:00', '18:15:00', 'present'),
('EMP00004', '2026-05-04', '00:00:00', '00:00:00', 'absent'),
('EMP00005', '2026-05-04', '07:45:00', '16:45:00', 'present'),
('EMP00001', '2026-05-05', '08:05:00', '17:40:00', 'present'),
('EMP00002', '2026-05-05', '08:10:00', '17:05:00', 'present'),
('EMP00003', '2026-05-05', '08:20:00', '18:00:00', 'present'),
('EMP00004', '2026-05-05', '08:12:00', '17:00:00', 'present'),
('EMP00005', '2026-05-05', '00:00:00', '00:00:00', 'conge'),
('EMP00006', '2026-05-04', '08:30:00', '17:30:00', 'present'),
('EMP00007', '2026-05-04', '07:50:00', '17:00:00', 'present'),
('EMP00008', '2026-05-04', '08:05:00', '17:45:00', 'present'),
('EMP00009', '2026-05-04', '08:40:00', '18:00:00', 'present'),
('EMP00010', '2026-05-04', '08:10:00', '17:00:00', 'present'),
('EMP00006', '2026-05-05', '08:15:00', '17:35:00', 'present'),
('EMP00007', '2026-05-05', '07:55:00', '17:15:00', 'present'),
('EMP00008', '2026-05-05', '00:00:00', '00:00:00', 'absent'),
('EMP00009', '2026-05-05', '08:20:00', '18:05:00', 'present'),
('EMP00010', '2026-05-05', '08:00:00', '17:00:00', 'present'),
('EMP00011', '2026-05-04', '07:50:00', '17:00:00', 'present'),
('EMP00012', '2026-05-04', '08:10:00', '17:30:00', 'present'),
('EMP00013', '2026-05-04', '07:40:00', '17:05:00', 'present'),
('EMP00014', '2026-05-04', '08:30:00', '18:00:00', 'present'),
('EMP00015', '2026-05-04', '08:25:00', '17:05:00', 'present'),
('EMP00011', '2026-05-05', '08:02:00', '17:10:00', 'present'),
('EMP00012', '2026-05-05', '08:15:00', '17:25:00', 'present'),
('EMP00013', '2026-05-05', '07:55:00', '17:00:00', 'present'),
('EMP00014', '2026-05-05', '08:45:00', '18:15:00', 'present'),
('EMP00015', '2026-05-05', '00:00:00', '00:00:00', 'conge'),
-- Maimouna Diallo (Cumule des absences en juin)
('EMP00004', '2026-06-01', '00:00:00', '00:00:00', 'absent'),
('EMP00004', '2026-06-02', '00:00:00', '00:00:00', 'absent'),
('EMP00004', '2026-06-03', '00:00:00', '00:00:00', 'absent'),
('EMP00004', '2026-06-04', '00:00:00', '00:00:00', 'absent'),
-- Cheikh Tidiane Ndiaye (Présent)
('EMP00001', '2026-06-01', '07:45:00', '18:00:00', 'present'),
('EMP00001', '2026-06-02', '08:00:00', '17:30:00', 'present'),
-- Aminata Diop (Présente)
('EMP00002', '2026-06-01', '08:10:00', '17:00:00', 'present'),
('EMP00002', '2026-06-02', '08:05:00', '17:15:00', 'present');



INSERT INTO Conge (employe_conge, date_debut_conge, date_fin_conge, type_conge, statut_conge) VALUES
('EMP00005', '2026-05-05', '2026-05-12', 'annuel', 'approuve'),
('EMP00015', '2026-05-05', '2026-05-06', 'maladie', 'approuve'),
('EMP00002', '2026-06-10', '2026-06-24', 'annuel', 'demande'),
('EMP00004', '2026-04-01', '2026-04-05', 'annuel', 'refuse'),
('EMP00006', '2026-08-15', '2026-08-30', 'annuel', 'approuve'),
('EMP00012', '2026-03-10', '2026-03-12', 'maladie', 'approuve'),
('EMP00016', '2026-01-05', '2026-01-15', 'annuel', 'approuve'),
('EMP00020', '2026-07-01', '2026-10-31', 'maternite', 'approuve'),
('EMP00009', '2026-02-01', '2026-02-10', 'annuel', 'refuse'),
('EMP00018', '2026-05-15', '2026-05-20', 'maladie', 'demande');



INSERT INTO Bulletin (employe_bulletin, mois_bulletin, annee_bulletin, salaire_brut, nb_jours_travailles, nb_absences_injustifiees, retenues, salaire_net) VALUES
('EMP00001', 3, 2026, 3800000, 22, 0, 0, 3800000),
('EMP00002', 3, 2026, 1600000, 22, 0, 0, 1600000),
('EMP00003', 3, 2026, 1950000, 22, 0, 0, 1950000),
-- EMP00004 : 2 absences en mars -> Retenue = (850 000 / 22) * 2 = 77 273
('EMP00004', 3, 2026, 850000, 20, 2, 77273, 772727),
('EMP00005', 3, 2026, 1300000, 22, 0, 0, 1300000),
('EMP00006', 3, 2026, 550000, 22, 0, 0, 550000),
('EMP00007', 3, 2026, 1700000, 22, 0, 0, 1700000),
('EMP00008', 3, 2026, 1850000, 22, 0, 0, 1850000),
('EMP00009', 3, 2026, 1900000, 22, 0, 0, 1900000),
('EMP00010', 3, 2026, 200000, 22, 0, 0, 200000),
('EMP00011', 3, 2026, 1550000, 22, 0, 0, 1550000),
('EMP00012', 3, 2026, 800000, 22, 0, 0, 800000),
('EMP00013', 3, 2026, 3600000, 22, 0, 0, 3600000),
('EMP00014', 3, 2026, 3500000, 22, 0, 0, 3500000),
('EMP00015', 3, 2026, 250000, 22, 0, 0, 250000),
('EMP00016', 3, 2026, 1250000, 22, 0, 0, 1250000),
('EMP00017', 3, 2026, 1650000, 22, 0, 0, 1650000),
('EMP00018', 3, 2026, 500000, 22, 0, 0, 500000),
('EMP00019', 3, 2026, 1800000, 22, 0, 0, 1800000),
('EMP00020', 3, 2026, 1500000, 22, 0, 0, 1500000),
('EMP00001', 4, 2026, 3800000, 22, 0, 0, 3800000),
('EMP00002', 4, 2026, 1600000, 22, 0, 0, 1600000),
('EMP00003', 4, 2026, 1950000, 22, 0, 0, 1950000),
('EMP00004', 4, 2026, 850000, 22, 0, 0, 850000), -- Tout le mois fait en avril !
('EMP00005', 4, 2026, 1300000, 22, 0, 0, 1300000),
('EMP00006', 4, 2026, 550000, 22, 0, 0, 550000),
('EMP00007', 4, 2026, 1700000, 22, 0, 0, 1700000),
('EMP00008', 4, 2026, 1850000, 22, 0, 0, 1850000),
('EMP00009', 4, 2026, 1900000, 22, 0, 0, 1900000),
('EMP00010', 4, 2026, 200000, 22, 0, 0, 200000),
('EMP00011', 4, 2026, 1550000, 22, 0, 0, 1550000),
('EMP00012', 4, 2026, 800000, 22, 0, 0, 800000),
('EMP00013', 4, 2026, 3600000, 22, 0, 0, 3600000),
('EMP00014', 4, 2026, 3500000, 22, 0, 0, 3500000),
('EMP00015', 4, 2026, 250000, 22, 0, 0, 250000),
('EMP00016', 4, 2026, 1250000, 22, 0, 0, 1250000),
('EMP00017', 4, 2026, 1650000, 22, 0, 0, 1650000),
('EMP00018', 4, 2026, 500000, 22, 0, 0, 500000),
('EMP00019', 4, 2026, 1800000, 22, 0, 0, 1800000),
('EMP00020', 4, 2026, 1500000, 22, 0, 0, 1500000),
('EMP00001', 5, 2026, 3800000, 22, 0, 0, 3800000),
('EMP00002', 5, 2026, 1600000, 22, 0, 0, 1600000),
('EMP00003', 5, 2026, 1950000, 22, 0, 0, 1950000),
-- EMP00004 : 1 absence -> Retenue = 850 000 / 22 = 38 636
('EMP00004', 5, 2026, 850000, 21, 1, 38636, 811364),
('EMP00005', 5, 2026, 1300000, 17, 0, 0, 1300000), -- 5 jours de congé, 0 absence injustifiée
('EMP00006', 5, 2026, 550000, 22, 0, 0, 550000),
('EMP00007', 5, 2026, 1700000, 22, 0, 0, 1700000),
-- EMP00008 : 1 absence -> Retenue = 1 850 000 / 22 = 84 091
('EMP00008', 5, 2026, 1850000, 21, 1, 84091, 1765909),
('EMP00009', 5, 2026, 1900000, 22, 0, 0, 1900000),
('EMP00010', 5, 2026, 200000, 22, 0, 0, 200000),
('EMP00011', 5, 2026, 1550000, 22, 0, 0, 1550000),
('EMP00012', 5, 2026, 800000, 22, 0, 0, 800000),
('EMP00013', 5, 2026, 3600000, 22, 0, 0, 3600000),
('EMP00014', 5, 2026, 3500000, 22, 0, 0, 3500000),
('EMP00015', 5, 2026, 250000, 20, 0, 0, 250000), -- 2 jours de maladie, 0 absence injustifiée
('EMP00016', 5, 2026, 1250000, 22, 0, 0, 1250000),
('EMP00017', 5, 2026, 1650000, 22, 0, 0, 1650000),
('EMP00018', 5, 2026, 500000, 22, 0, 0, 500000),
('EMP00019', 5, 2026, 1800000, 22, 0, 0, 1800000),
('EMP00020', 5, 2026, 1500000, 22, 0, 0, 1500000),
('EMP00001', 6, 2026, 3800000, 22, 0, 0, 3800000),
('EMP00002', 6, 2026, 1600000, 22, 0, 0, 1600000),
('EMP00003', 6, 2026, 1950000, 22, 0, 0, 1950000),
-- EMP00004 : 4 absences en juin -> Retenue = (850 000 / 22) * 4 = 154 545
('EMP00004', 6, 2026, 850000, 18, 4, 154545, 695455),
('EMP00005', 6, 2026, 1300000, 22, 0, 0, 1300000),
('EMP00006', 6, 2026, 550000, 22, 0, 0, 550000),
('EMP00007', 6, 2026, 1700000, 22, 0, 0, 1700000),
('EMP00008', 6, 2026, 1850000, 22, 0, 0, 1850000),
('EMP00009', 6, 2026, 1900000, 22, 0, 0, 1900000),
('EMP00010', 6, 2026, 200000, 22, 0, 0, 200000),
('EMP00011', 6, 2026, 1550000, 22, 0, 0, 1550000),
('EMP00012', 6, 2026, 800000, 22, 0, 0, 800000),
('EMP00013', 6, 2026, 3600000, 22, 0, 0, 3600000),
('EMP00014', 6, 2026, 3500000, 22, 0, 0, 3500000),
('EMP00015', 6, 2026, 250000, 22, 0, 0, 250000),
('EMP00016', 6, 2026, 1250000, 22, 0, 0, 1250000),
('EMP00017', 6, 2026, 1650000, 22, 0, 0, 1650000),
('EMP00018', 6, 2026, 500000, 22, 0, 0, 500000),
('EMP00019', 6, 2026, 1800000, 22, 0, 0, 1800000),
('EMP00020', 6, 2026, 1500000, 22, 0, 0, 1500000);






-- Requêtes de consultation

-- 1. Masse salariale mensuelle par département (GROUP BY + SUM)
SELECT 
    d.nom_dpt AS 'Département',
    SUM(b.salaire_brut) AS 'Masse Salariale Mensuelle (FCFA)'
FROM 
    Bulletin b
JOIN 
    Employe e ON b.employe_bulletin = e.matricule
JOIN 
    Departement d ON e.code_dpt = d.code_dpt
WHERE 
    b.mois_bulletin = MONTH(CURDATE()) -- Dynamique : mois en cours
    AND b.annee_bulletin = YEAR(CURDATE()) -- Dynamique : année en cours
GROUP BY 
    d.code_dpt, d.nom_dpt;  



-- 2. Employés ayant plus de 3 absences injustifiées ce mois (HAVING)
SELECT 
    e.matricule, 
    e.nom_emp, 
    e.prenom_emp,
    SUM(b.nb_absences_injustifiees) AS 'Total Absences'
FROM 
    Bulletin b
JOIN 
    Employe e ON b.employe_bulletin = e.matricule
WHERE 
    b.mois_bulletin = MONTH(CURDATE()) -- Dynamique
    AND b.annee_bulletin = YEAR(CURDATE()) -- Dynamique
GROUP BY 
    e.matricule, e.nom_emp, e.prenom_emp
HAVING 
    SUM(b.nb_absences_injustifiees) > 3;


-- 3. Employés dont le contrat CDD expire dans moins de 30 jours
SELECT 
    e.matricule, 
    e.nom_emp, 
    e.prenom_emp, 
    c.type_contrat, 
    c.date_fin_contrat
FROM 
    Contrat c
JOIN 
    Employe e ON c.employe_contrat = e.matricule
WHERE 
    c.type_contrat = 'CDD'
    AND c.date_fin_contrat >= CURDATE()
    AND c.date_fin_contrat <= DATE_ADD(CURDATE(), INTERVAL 30 DAY);





-- 4. Solde de congés restants par employé pour l’année en cours

SELECT 
    e.matricule AS 'Matricule',
    e.nom_emp AS 'Nom',
    e.prenom_emp AS 'Prénom',
    -- On considère un droit standard de 30 jours de congé par an
    30 - IFNULL(SUM(DATEDIFF(c.date_fin_conge, c.date_debut_conge) + 1), 0) AS 'Solde Congés Restants (Jours)'
FROM 
    Employe e
LEFT JOIN 
    Conge c ON e.matricule = c.employe_conge 
    AND c.statut_conge = 'approuve'
    AND c.type_conge = 'annuel'
    AND YEAR(c.date_debut_conge) = YEAR(CURDATE()) -- Année en cours
GROUP BY 
    e.matricule, e.nom_emp, e.prenom_emp;






-- 5. Employé le mieux payé par département (sous-requête corrélée)

SELECT 
    d.nom_dpt AS 'Département',
    e.matricule AS 'Matricule',
    e.nom_emp AS 'Nom',
    e.prenom_emp AS 'Prénom',
    b.salaire_net AS 'Salaire Net (FCFA)'
FROM 
    Bulletin b
JOIN 
    Employe e ON b.employe_bulletin = e.matricule
JOIN 
    Departement d ON e.code_dpt = d.code_dpt
WHERE 
    b.mois_bulletin = MONTH(CURDATE()) -- Changé ici
    AND b.annee_bulletin = YEAR(CURDATE()) -- Changé ici
    AND b.salaire_net = (
        SELECT MAX(b2.salaire_net)
        FROM Bulletin b2
        JOIN Employe e2 ON b2.employe_bulletin = e2.matricule
        WHERE e2.code_dpt = e.code_dpt 
        AND b2.mois_bulletin = MONTH(CURDATE()) -- Changé ici aussi
        AND b2.annee_bulletin = YEAR(CURDATE()) -- Changé ici aussi
    );






-- 6. Taux d’absentéisme par département sur les 3 derniers mois

SELECT 
    d.nom_dpt AS 'Département',
    SUM(b.nb_absences_injustifiees) AS 'Total Jours Absences',
    SUM(b.nb_jours_travailles + b.nb_absences_injustifiees) AS 'Total Jours Théoriques',
    -- Calcul du taux en pourcentage
    ROUND(
        (SUM(b.nb_absences_injustifiees) / SUM(b.nb_jours_travailles + b.nb_absences_injustifiees)) * 100, 
        2
    ) AS 'Taux d\'absentéisme (%)'
FROM 
    Bulletin b
JOIN 
    Employe e ON b.employe_bulletin = e.matricule
JOIN 
    Departement d ON e.code_dpt = d.code_dpt
WHERE 
    -- Filtre 100% dynamique basé sur la date du jour (CURDATE)
    STR_TO_DATE(CONCAT(b.annee_bulletin, '-', b.mois_bulletin, '-01'), '%Y-%m-%d') 
        BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND CURDATE()
GROUP BY 
    d.code_dpt, d.nom_dpt;