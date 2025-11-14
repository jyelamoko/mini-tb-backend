-- ==========================================
-- V2 : Ajout de la colonne user_id dans consultants
--       + ajout de la foreign key
--       + mapping consultant → user
-- ==========================================

-- 1) Ajouter la colonne user_id
ALTER TABLE consultants
    ADD COLUMN user_id BIGINT NULL;

-- 2) Ajouter la contrainte FK
ALTER TABLE consultants
    ADD CONSTRAINT fk_consultant_user
        FOREIGN KEY (user_id) REFERENCES users(id);

-- 3) Associer automatiquement les consultants aux bons users

-- Steven King -> user 1 (rh)
UPDATE consultants SET user_id = 1 WHERE id = 1;

-- Martin Lucas -> user 2 (dir)
UPDATE consultants SET user_id = 2 WHERE id = 2;

-- Nguyen Sophie -> user 3 (consult)
UPDATE consultants SET user_id = 3 WHERE id = 3;

-- Moreau Nina -> user 4 (admin)
UPDATE consultants SET user_id = 4 WHERE id = 8;

