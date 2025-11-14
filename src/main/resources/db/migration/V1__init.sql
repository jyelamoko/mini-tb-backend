-- =============================================
-- V1 : Création du schéma complet
-- =============================================

-- ======= consultants ==========================
CREATE TABLE consultants (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(100),
    status VARCHAR(50),
    hire_date DATE NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(255));

-- ======= projects =============================
CREATE TABLE projects (
    id BIGINT PRIMARY KEY,
    client VARCHAR(150) NOT NULL,
    description VARCHAR(255),
    end_date DATE,
    project_name VARCHAR(150) NOT NULL,
    start_date DATE NOT NULL,
    state VARCHAR(255));

-- ======= performances =========================
CREATE TABLE performances (
    id BIGINT PRIMARY KEY,
    consultant_id BIGINT NOT NULL,
    comments VARCHAR(255),
    evaluation_note FLOAT,
    month_perf VARCHAR(7) NOT NULL,
    occupancy_rate FLOAT,
    CONSTRAINT fk_perf_consult FOREIGN KEY (consultant_id) REFERENCES consultants(id),
    CONSTRAINT uk_perf UNIQUE (consultant_id, month_perf));

-- ======= missions ==============================
CREATE TABLE missions (
    id BIGINT PRIMARY KEY,
    end_date_mission DATE,
    role VARCHAR(100),
    start_date_mission DATE NOT NULL,
    id_consultant BIGINT NOT NULL,
    id_project BIGINT NOT NULL,
    CONSTRAINT fk_miss_proj FOREIGN KEY (id_project) REFERENCES projects(id),
    CONSTRAINT fk_miss_consult FOREIGN KEY (id_consultant) REFERENCES consultants(id));

-- ======= users ================================
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    passwordhash VARCHAR(255) NOT NULL);

-- ======= user_roles ============================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(100) NOT NULL,
    CONSTRAINT fk_roles_user FOREIGN KEY (user_id) REFERENCES users(id));

-- =============================================
-- INSERT DATA
-- =============================================

-- ===== consultants ============================
INSERT INTO consultants (id, first_name, last_name, job_title, status, hire_date, email, phone_number) VALUES
    (1, 'Steven', 'King', 'Tech Lead Dev Java', 'Actif', '2021-09-01', 's.king@entreprise.com', '0615263748'),
    (2, 'Martin', 'Lucas', 'Développeur FullStack', 'Actif', '2021-09-01', 'lucas.martin@entreprise.com', '0605060708'),
    (3, 'Nguyen', 'Sophie', 'Chef de projet IT', 'En mission', '2020-11-15', 'sophie.nguyen@entreprise.com', '0611223344'),
    (4, 'Diallo', 'Amadou', 'Data Analyst', 'Actif', '2022-05-20', 'amadou.diallo@entreprise.com', '0622334455'),
    (5, 'Dupont', 'Julien', 'Consultant Sécurité', 'Disponible', '2021-02-12', 'julien.dupont@entreprise.com', '0644556677'),
    (6, 'Benali', 'Karim', 'Développeur Backend', 'Actif', '2020-06-22', 'karim.benali@entreprise.com', '0677889900'),
    (7, 'Lemoine', 'Alice', 'Consultante CRM', 'Actif', '2019-08-01', 'alice.lemoine@entreprise.com', '0633445566'),
    (8, 'Moreau', 'Nina', 'UX Designer', 'En mission', '2021-01-05', 'nina.moreau@entreprise.com', '0611223344');

-- ===== projects ===============================
INSERT INTO projects (id, project_name, client, description, start_date, end_date, state) VALUES
    (1, 'DigitalRH', 'BanqueX', 'Mise en place d’un outil RH digitalisé', '2023-01-10', '2023-12-31', 'Terminé'),
    (2, 'CRMConnect', 'AssurTech', 'Déploiement d’un CRM personnalisé', '2023-04-01', NULL, 'En cours'),
    (3, 'SecureCloud', 'DataCorp', 'Audit de sécurité et migration cloud', '2024-01-15', NULL, 'En cours'),
    (4, 'Insight360', 'RetailSoft', 'Outil BI pour la direction commerciale', '2023-10-01', NULL, 'En cours'),
    (5, 'UXRefonte', 'WebMarket', 'Refonte ergonomique du site e-commerce', '2024-03-01', NULL, 'En cours');

-- ===== performances ============================
INSERT INTO performances (id, consultant_id, month_perf, occupancy_rate, evaluation_note, comments) VALUES
    (1, 1, '2024-01', 95.00, 9.2, 'Très bon suivi de mission'),
    (2, 1, '2024-02', 90.00, 9.0, 'Bonne implication'),
    (3, 2, '2024-03', 85.00, 8.7, 'Livraisons dans les temps'),
    (4, 2, '2024-04', 92.00, 8.9, 'Excellent travail sur module API'),
    (5, 3, '2024-01', 88.00, 9.3, 'Leadership reconnu'),
    (6, 4, '2024-03', 80.00, 8.1, 'Analyse précise et synthétique'),
    (7, 5, '2024-02', 70.00, 7.5, 'Bon démarrage de mission'),
    (8, 6, '2024-03', 82.00, 8.3, 'Bonne performance technique'),
    (9, 7, '2024-04', 91.00, 9.1, 'Très bonne relation client'),
    (10, 8, '2024-04', 88.00, 8.9, 'Bonne créativité');

-- ===== missions ================================
INSERT INTO missions (id, id_consultant, id_project, role, start_date_mission, end_date_mission) VALUES
    (1, 1, 1, 'Consultante RH Senior', '2023-01-15', '2023-11-30'),
    (2, 2, 2, 'Développeur FullStack', '2023-04-10', '2024-04-30'),
    (3, 3, 3, 'Chef de projet IT', '2024-01-20', '2024-06-20'),
    (4, 4, 4, 'Data Analyst', '2023-10-15', '2023-12-31'),
    (5, 5, 3, 'Consultant Sécurité', '2024-02-01', '2024-06-30'),
    (6, 6, 3, 'Développeur Backend', '2024-01-25', '2024-04-25'),
    (7, 7, 2, 'Consultante CRM', '2023-05-10', '2024-05-10'),
    (8, 8, 5, 'UX Designer', '2024-03-10', '2024-12-10');

-- ===== users (PASSWORDS VALIDÉS) ===============
INSERT INTO users (id, username, passwordhash) VALUES
    (1, 'rh',      '$2a$12$/1oiykyZ3IzO07HvlE.XZuGSrs/Yoo24VyiaFcVsE64txRUmg5wMa'),
    (2, 'dir',     '$2a$12$5.yHzK52LIu/fvX0P/oI0udMOVPRZGC0OkGwLNxevO0wF/XQUWmyK'),
    (3, 'consult', '$2a$12$LeO18vIarw46j6ptIvRNHOO8jfyA40Dw9tbJLBousGdUTv4G81Fya'),
    (4, 'admin',   '$2a$12$KHobhu0vaWkHQ9mjDrFBGOYrGUzz../9rngnvc6toIneHzo8fn6jm');

-- ===== user_roles ==============================
INSERT INTO user_roles (user_id, role) VALUES
    (1, 'RH'),
    (2, 'DIRECTION'),
    (3, 'CONSULTANT'),
    (4, 'RH'),
    (4, 'DIRECTION');
