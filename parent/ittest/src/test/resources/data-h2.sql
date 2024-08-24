INSERT INTO documents(
	id, titre, description, etat,typemouvement,afficherunite,afficherdistributeur,prixeditable,contientressources,afficherprix, datecreation, datemodification)
	VALUES 
('0190615e-1101-7209-9932-7020bbd556f1', 'Note intervention', 'Document delivre par le medecin ou un infirmier de l''etablissement', true, 'Ajout', true, true, true, true,true,'2023-03-31','2023-03-31'),
('0190615e-1101-7209-9932-7020bbd556f2','Fiche de suivi','Document delivre par le medecin ou un infirmier de l''etablissement',True,'Neutre',true,true,true,true,true,'2023-03-31','2023-03-31'),
('0190615e-1101-7209-9932-7020bbd556f3','Fiche de soin','Document delivre par le medecin ou un infirmier de l''etablissement',True,'Ajout',true,true,true,true,true,NULL,NULL),
('0190615e-1101-7209-9932-7020bbd556f4','Formulaire de sortie','Document delivre par le medecin ou un infirmier de l''etablissement',True,'Neutre',true,true,true,true,true,NULL,NULL),
('0190615e-1101-7209-9932-7020bbd556f5','ordonnance','Document delivre par le medecin ou un infirmier de l''etablissement',True,'Neutre',true,true,true,true,true,NULL,NULL),
('0190615e-1101-7209-9932-7020bbd556f6','Bon de commande','Document delivre par le chef service',True,'Neutre',true,true,true,true,true,NULL,NULL),
('0190615e-1101-7209-9932-7020bbd556f7','Bon de livraison','Document delivre par ',True,'Reduire',true,true,true,true,true,NULL,NULL),
('0190615e-1101-7209-9932-7020bbd556f8','Fiche de selection','Document delivre par le magasinier',True,'Neutre',true,true,true,true,true,NULL,NULL);


-- Insertion dans la table service
INSERT INTO services (id, libelle, description, codeunique, etat,localisation, datecreation, datemodification)
VALUES
('2190615e-1101-7209-9932-7020bbd556f1', 'Consultation', 'bien', 'S1', true, 'douala', '1972-06-12', '1990-03-07'),
('2190615e-1101-7209-9932-7020bbd556f2', 'Laboratoire', 'bien', 'S2', true, 'douala', '1990-08-06', '1990-03-07'),
('2190615e-1101-7209-9932-7020bbd556f3', 'Pharmacie', 'bien', 'S3', true, 'douala', '2000-03-07', '1990-03-07');

-- Insertion dans la table mission
INSERT INTO missions (id, libelle, description, etat, datecreation, datemodification, services_id)
VALUES
('3190615e-1101-7209-9932-7020bbd556f1', 'Encaissement', 'recu de paiement lié à une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1'),
('3190615e-1101-7209-9932-7020bbd556f2', 'Resultat Labo', 'Communiquer les résultats du labo aux patients', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1'),
('3190615e-1101-7209-9932-7020bbd556f3', 'Hospitalisation', 'bon d''entrée et de sortie est une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2'),
('3190615e-1101-7209-9932-7020bbd556f4', 'Prelevement Labo', 'Prélévement fait par laboratoire', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2'),
('3190615e-1101-7209-9932-7020bbd556f5', 'Consultation Spécialiste', 'Consultation faite par un médecin', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3'),
('3190615e-1101-7209-9932-7020bbd556f6', 'Consultation', 'Consultation faite par une infirmière', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3');

INSERT INTO attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut)
VALUES
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Taille', 'Description Taille', TRUE, '2022-01-01', '2022-01-02', 'String', null),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Poids', 'Description Poids', TRUE, '2022-01-01', '2022-01-02', 'Double', null),
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Age', 'Description age', TRUE, '2022-01-01', '2022-01-02', 'Int', null),
('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Sexe', 'Sexe ', TRUE, '2022-01-01', '2022-01-02', 'String', null),
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Teint', 'Coloration', TRUE, '2022-01-01', '2022-01-02', 'String', 'Noir'),
('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Groupe sangin', 'Groupe sangin', TRUE, '2022-01-01', '2022-01-02', 'String', 'A, A+, A-, B, B+, B-, AB, AB+, AB-, O, O+, O-'),
('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Allergies', 'Allergies connues ', TRUE, '2022-01-01', '2022-01-02', 'String', null),
('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Nom', 'Nom de la personne', TRUE, '2022-01-01', '2022-01-02', 'String', null),
('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Adresse', 'Adresse postale', TRUE, '2022-01-01', '2022-01-02', 'String', null),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Date de naissance', 'Date de naissance', TRUE, '2022-01-01', '2022-01-02', 'Date', null);

-- Insertion dans la table 'categories'
INSERT INTO categories (id, libelle, ordre, etat, datecreation, datemodification,documents_id)
VALUES
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'Informations Personnelles', 0, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f1'),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'Informations Primaires', 1, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f1'),
('be8ef47f-d5a2-4254-ab79-af860672553e', 'Conditions Générales',0, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f2'),
('be8ef57f-d5a2-4254-ab79-af860672553e', 'Conditions Particulières', 1, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f2'),
('be8ef56f-d5a2-4254-ab79-af860672553e', 'Informations Personnelles', 1, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f3'),
('be8ef59f-d5a2-4254-ab79-af860672553e', 'Informations Primaires', 1, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f4'),
('be8ef59f-d5a3-4254-ab79-af860672553e', 'Conditions Générales', 1, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f5'),
('be8ef59f-d5a4-4254-ab79-af860672553e', 'Conditions Générales', 0, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f6'),
('be8ef59f-d5a4-4254-ab99-af860672553e', 'Catégorie par Defaut', 0, true, '2022-01-01', '2022-01-02','0190615e-1101-7209-9932-7020bbd556f7');

-- Insertion dans la table 'associer'
INSERT INTO associer (categories_id, attributs_id,ordre,obligatoire)
VALUES
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',1,true),
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',2,true),
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',3,false),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',1,true),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',2,false),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20',3,true),
('be8ef47f-d5a2-4254-ab79-af860672553e', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',1,true),
('be8ef57f-d5a2-4254-ab79-af860672553e', 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14',1,true),
('be8ef56f-d5a2-4254-ab79-af860672553e', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',1,true),
('be8ef59f-d5a2-4254-ab79-af860672553e', 'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16',1,true),
('be8ef59f-d5a3-4254-ab79-af860672553e', 'a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17',1,true),
('be8ef59f-d5a4-4254-ab79-af860672553e', 'a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',1,true),
('be8ef59f-d5a4-4254-ab99-af860672553e', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',1,true);

-- Insertion dans la table 'constituer'
INSERT INTO constituer (documents_id, attributs_id)
VALUES
('0190615e-1101-7209-9932-7020bbd556f1', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
('0190615e-1101-7209-9932-7020bbd556f1', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13'),
('0190615e-1101-7209-9932-7020bbd556f1', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),
('0190615e-1101-7209-9932-7020bbd556f1', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12'),
('0190615e-1101-7209-9932-7020bbd556f1', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19'),
('0190615e-1101-7209-9932-7020bbd556f1', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20'),
('0190615e-1101-7209-9932-7020bbd556f2', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13'),
('0190615e-1101-7209-9932-7020bbd556f2', 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14'),
('0190615e-1101-7209-9932-7020bbd556f2', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20'),
('0190615e-1101-7209-9932-7020bbd556f3', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15'),
('0190615e-1101-7209-9932-7020bbd556f4', 'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16'),
('0190615e-1101-7209-9932-7020bbd556f5', 'a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17'),
('0190615e-1101-7209-9932-7020bbd556f6', 'a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18'),
('0190615e-1101-7209-9932-7020bbd556f7', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19'),
('0190615e-1101-7209-9932-7020bbd556f8', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20');

-- Insertion dans la table 'precomouvements'
INSERT INTO precomouvements (id, libelle, etat, typemouvement, datecreation, datemodification) VALUES
('6290615e-1101-7209-9932-7020bbd556f1', 'Inventaire', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f2', 'Vente', true, 'Reduire', '2022-01-01', '2022-01-02'),
('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', 'Achat', true, 'Ajout', '2022-01-01', '2022-01-02'),
('77b8577f-6d26-4376-af30-a3c8f75a9194', 'interdiction Infirmière', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f5', 'Don', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f6', 'Perte', true, 'Reduire', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f7', 'Retour', true, 'Ajout', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f8', 'Sortie magasin', true, 'Reduire', '2022-01-01', '2022-01-02');

---- Insertion dans la table 'familles'
INSERT INTO familles (id, libelle, description, etat, datecreation, datemodification) VALUES
('f190615e-1101-7209-9932-7020bbd556f1', 'Medicaments', 'Medicaments', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f2', 'Consommables Informatiques', 'Consommables Informatiques', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f3', 'BioMedical', 'BioMedical', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f4', 'Accessoires', 'Accessoires', true, '2022-01-01', '2022-01-02');

--
---- Insertion dans la table 'ressources'
INSERT INTO ressources (id, libelle, description, etat, datecreation, datemodification,quantite, prixentree,prixsortie,seuilalerte,unite, familles_id) VALUES
('6190615e-1101-7209-9932-7020bbd556f1', 'Paracetamol', 'Paracetamol', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f1'),
('6190615e-1101-7209-9932-7020bbd556f2', 'Cartouche d''encre', 'Cartouche d''encre', true, '2022-01-01', '2022-01-02',4,2000,3000,10,'Boite', 'f190615e-1101-7209-9932-7020bbd556f2'),
('6190615e-1101-7209-9932-7020bbd556f3', 'Scanner', 'Scanner', true, '2022-01-01', '2022-01-02',4,12000,20000,2,'Packs', 'f190615e-1101-7209-9932-7020bbd556f2'),
('6190615e-1101-7209-9932-7020bbd556f4', 'Imprimante', 'Imprimante', true, '2022-01-01', '2022-01-02',4,12000,20000,2,'Packs', 'f190615e-1101-7209-9932-7020bbd556f2'),
('6190615e-1101-7209-9932-7020bbd556f5', 'Souris', 'Souris', true, '2022-01-01', '2022-01-02',40,25,20,12,'Packs', 'f190615e-1101-7209-9932-7020bbd556f2'),
('6190615e-1101-7209-9932-7020bbd556f6', 'Stylet', 'Stylet', true, '2022-01-01', '2022-01-02',24,20,20000,9,'Packs', 'f190615e-1101-7209-9932-7020bbd556f2'),
('6190615e-1101-7209-9932-7020bbd556f7', 'Doliprane', 'Doliprane', true, '  2022-01-01', '2022-01-02',90,1200,2000,40,'Boite', 'f190615e-1101-7209-9932-7020bbd556f1'),
('6190615e-1101-7209-9932-7020bbd556f8', 'Pommade', 'Pommade', true, '2022-01-01', '2022-01-02',20,200,210,20,'Litre', 'f190615e-1101-7209-9932-7020bbd556f1'),
('6190615e-1101-7209-9932-7020bbd556f9', 'Seringue', 'Seringue', true, '2022-01-01', '2022-01-02',42,100,200,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f3'),
('6190615e-1101-7209-9932-7020bbd556f0', 'Perfuseur', 'Perfuseur', true, '2022-01-01', '2022-01-02',5,90,230,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f3'),
('5f516ee3-b23d-4cf1-8d55-e301764e0501', 'Gants', 'Gants', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f3'),
('5f516ee3-b23d-4cf1-8d55-e301764e0502', 'Masque', 'Masque', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f3'),
('5f516ee3-b23d-4cf1-8d55-e301764e0503', 'Gel Hydroalcoolique', 'Gel Hydroalcoolique', true, '2022-01-01', '2022-01-02',54,1200,2000,200,'Litre', 'f190615e-1101-7209-9932-7020bbd556f3'),
('5f516ee3-b23d-4cf1-8d55-e301764e0504', 'Coton', 'Coton', true, '2022-01-01', '2022-01-02',44,1200,2000,230,'Boite', 'f190615e-1101-7209-9932-7020bbd556f3'),
('5f516ee3-b23d-4cf1-8d55-e301764e0505', 'Papier', 'Papier', true, '2022-01-01', '2022-01-02',24,1200,2000,20,'Packs', 'f190615e-1101-7209-9932-7020bbd556f4'),
('5f516ee3-b23d-4cf1-8d55-e301764e0506', 'Encre', 'Encre', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f4'),
('5f516ee3-b23d-4cf1-8d55-e301764e0507', 'Toner', 'Toner', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Boite', 'f190615e-1101-7209-9932-7020bbd556f4'),
('5f516ee3-b23d-4cf1-8d55-e301764e0508', 'Cahier', 'Cahier', true, '2022-01-01', '2022-01-02',4,1200,2000,20,'Packs', 'f190615e-1101-7209-9932-7020bbd556f4');

---- Insertion dans la table 'precomouvementsqtes'
INSERT INTO precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, precomouvements_id, ressources_id, datecreation, datemodification) VALUES
('a97eb081-62f6-4617-ba29-64dc8593a9ff', 10, 100, 500, 5000, '6290615e-1101-7209-9932-7020bbd556f1', '6190615e-1101-7209-9932-7020bbd556f1', '2022-01-01', '2022-01-02'),
('a87eb081-62f6-4617-ba29-64dc8593a9ff', 20, 200, 1000, 10000, '6290615e-1101-7209-9932-7020bbd556f2', '6190615e-1101-7209-9932-7020bbd556f2', '2022-01-01', '2022-01-02'),
('a77eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', '6190615e-1101-7209-9932-7020bbd556f3', '2022-01-01', '2022-01-02'),
('a67eb081-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f4', '2022-01-01', '2022-01-02'),
('a97eb071-62f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '77b8577f-6d26-4376-af30-a3c8f75a9194', '6190615e-1101-7209-9932-7020bbd556f5', '2022-01-01', '2022-01-02'),
('a97eb081-72f6-4617-ba29-64dc8593a9ff', 5, 50, 200, 2000, '6290615e-1101-7209-9932-7020bbd556f6', null, '2022-01-01', '2022-01-02'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 5, 50, 200, 2000, '6290615e-1101-7209-9932-7020bbd556f7', null, '2022-01-01', '2022-01-02'),
('a97eb081-62f6-4617-ba29-65dc8593a9ff', 5, 50, 200, 2000, '6290615e-1101-7209-9932-7020bbd556f8', null, '2022-01-01', '2022-01-02'),
('a97eb081-62f6-4617-ba29-74dc8593a9ff', 5, 50, 200, 2000, '6290615e-1101-7209-9932-7020bbd556f8', null, '2022-01-01', '2022-01-02');

-- Insertion dans la table sapplique
INSERT INTO sapplique (precomouvementsqtes_id,familles_id)
VALUES
('a97eb081-72f6-4617-ba29-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f1'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f2'),
('a97eb081-62f6-4617-ba29-65dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f3'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f4'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f1'),
('a97eb081-72f6-4617-ba29-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f2'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f3'),
('a97eb081-62f6-4617-ba29-65dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f4'),
('a97eb081-62f6-4617-ba49-64dc8593a9ff', 'f190615e-1101-7209-9932-7020bbd556f1');

--insertion dans la table suivre
INSERT INTO suivre (precomouvements_id, documents_id)
VALUES
('6290615e-1101-7209-9932-7020bbd556f1', '0190615e-1101-7209-9932-7020bbd556f1'),
('6290615e-1101-7209-9932-7020bbd556f2', '0190615e-1101-7209-9932-7020bbd556f2'),
('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', '0190615e-1101-7209-9932-7020bbd556f3'),
('77b8577f-6d26-4376-af30-a3c8f75a9194', '0190615e-1101-7209-9932-7020bbd556f4'),
('6290615e-1101-7209-9932-7020bbd556f5', '0190615e-1101-7209-9932-7020bbd556f5'),
('6290615e-1101-7209-9932-7020bbd556f6', '0190615e-1101-7209-9932-7020bbd556f6'),
('6290615e-1101-7209-9932-7020bbd556f7', '0190615e-1101-7209-9932-7020bbd556f7'),
('6290615e-1101-7209-9932-7020bbd556f8', '0190615e-1101-7209-9932-7020bbd556f8');

-- Insérer les données dans la table 'personnes' en se basant sur la classe PersonnesEntity
--INSERT INTO personnes (id,adresse,mail,telephone,qrcodevalue,datecreation,datemodification) VALUES
--('1979bd79-f71b-498b-b247-e7b9bbb3f600','Bastos','abd@yah.fr','655454545','qrcode1','2022-01-01','2022-01-02'),
--('d301ff83-2a62-4e6d-aa23-57c7825bcd57','Bastos','dbc@yah.fr','655454545','qrcode2','2022-01-01','2022-01-02'),
--('0618e585-f82a-4d5f-af1c-54f880d766d3','Bastos','pnj@gma.com','655454545','qrcode3','2022-01-01','2022-01-02'),
--('0618e585-f82a-4d5f-af2c-54f880d766d3','Bastos','pnjer@gma.com','655454545','qrcode893','2022-01-01','2022-01-02'),
--('0618e585-f82a-4d5f-af2c-04f880d766d3','Nkoabang','pnjerrtt@gma.com','655454545','qrcode893','2022-01-01','2022-01-02'),
--('fdbff692-6b8d-43ab-a196-fcc3114b2daa','Bastos','sg@sg.fr','655454545','qrcode4','2022-01-01','2022-01-02'),
--('0079bd79-f71b-498b-b247-e7b9bbb3f600','@GOSPEL','der@yah.fr','655454545','qrcode22','2022-01-01','2022-01-02'),
--('0001ff83-2a62-4e6d-aa23-57c7825bcd57','@Sportif','erfd@yah.fr','655454545','qrcode12','2022-01-01','2022-01-02'),
--('0018e585-f82a-4d5f-af1c-54f880d766d3','@armee','agrt@yah.fr','655454545','qrcode32','2022-01-01','2022-01-02');

INSERT INTO personnes (id,nom,prenom,sexe,datenaissance,datemodification,person_type,comptes_id) VALUES
('0618e585-f82a-4d5f-af2c-04f880d766d3','Nkoabang','pnjerrtt@gma.com','Masculin','2022-01-01','2022-01-02', 'personnesphysique','d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e'),
('0618e585-f82a-4d5f-af2c-54f880d766d3','Essos','nayat@gma.com','Masculin','2022-01-01','2022-01-02', 'personnesphysique','a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f');

INSERT INTO personnes (id,raisonsociale,code,datemodification,person_type) VALUES
('0079bd79-f71b-498b-b247-e7b9bbb3f600','GOSPEL',43,'2022-01-01','personnesmorales'),
('0001ff83-2a62-4e6d-aa23-57c7825bcd57','Sportif',47,'2022-01-01','personnesmorales'),
('0018e585-f82a-4d5f-af1c-54f880d766d3','armee',45,'2022-01-01','personnesmorales');

-- Insérer les données dans la table 'distributeur' en se basant sur la classe DistributeurEntity
INSERT INTO personnes (id, code, raisonsociale, datemodification,person_type,comptes_id) VALUES
('1979bd79-f71b-498b-b247-e7b9bbb3f600','d190615','ENEO','2022-01-01','distributeurs','b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d'),
('0618e585-f82a-4d5f-af1c-54f880d766d3','d190616','Brasserie','2022-01-01','distributeurs','f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d');
INSERT INTO personnes (id, code, raisonsociale, datemodification,person_type) VALUES
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','d190619','UCB','2022-01-01','distributeurs'),
('fdbff692-6b8d-43ab-a196-fcc3114b2daa','d190614','Pharmacam','2022-01-01','distributeurs');

INSERT INTO personnels (id, dateentree, nom, datenaissance, telephone, datesortie, email, prenom, sexe)
VALUES
    ('4390615e-1101-7209-9932-7020bbd556f1', CURRENT_DATE, 'Tagne', '2000-04-10', '655455487', NULL, 'tagnewillie@gmail.com', 'Willy', 'M'),
    ('4390615e-1101-7209-9932-7020bbd556f2', CURRENT_DATE, 'Peter', '2004-08-10', '655455487', NULL, 'peteralan@gmail.com', 'Alan', 'M'),
    ('4390615e-1101-7209-9932-7020bbd556f3', CURRENT_DATE, 'Dombo', '2002-10-10', '655455487', NULL, 'dombogilles@gmail.com', 'Gilles', 'M');

INSERT INTO caisses (id, libelle, solde, type, detailJSON)
VALUES
    ('a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f', 'Caisse principale', 10000.00, 'Caisse', '{"description": "Caisse principale"}'),
    ('d9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e', 'Caisse secondaire', 5000.00, 'Caisse', '{"description": "Caisse secondaire"}'),
    ('b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d', 'Caisse en ligne', 7500.00, 'Caisse', '{"description": "Caisse en ligne"}'),
    ('f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d', 'Caisse mobile', 3000.00, 'Caisse', '{"description": "Caisse mobile"}');

INSERT INTO comptes (id, solde, datecreation, etat, montantdecouvertmax, libelle,personnes_id)
VALUES
    ('a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f', '1000.00', '2024-08-19', 'actif', 500.00, 'Compte courant','0618e585-f82a-4d5f-af2c-54f880d766d3'),
    ('d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e', '2500.00', '2024-08-19', 'actif', 1000.00, 'Épargne','0618e585-f82a-4d5f-af2c-04f880d766d3'),
    ('b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d', '500.00', '2024-08-19', 'inactif', 0.00, 'Compte jeunesse','1979bd79-f71b-498b-b247-e7b9bbb3f600'),
    ('f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d', '7500.00', '2024-08-19', 'actif', 2000.00, 'Compte entreprise','0618e585-f82a-4d5f-af1c-54f880d766d3');

INSERT INTO exemplaires (id,personnes_id) VALUES
('a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f','0618e585-f82a-4d5f-af2c-54f880d766d3'),
('d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e','0618e585-f82a-4d5f-af2c-04f880d766d3'),
('b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d','1979bd79-f71b-498b-b247-e7b9bbb3f600'),
('f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d','0618e585-f82a-4d5f-af1c-54f880d766d3');

-- Insertion dans la table 'mouvemnts'
INSERT INTO mouvements (id,description, quantite, prix, distributeurs_id, ressources_id, dateperemption, datecreation, datemodification,caisses_id,personnels_id,exemplaires_id,comptes_id) VALUES
('97d314f5-84ad-47cd-874d-9c55f0e45790', 'sur commande',  10, 5000,'1979bd79-f71b-498b-b247-e7b9bbb3f600',  '6190615e-1101-7209-9932-7020bbd556f1', null, '2022-01-01', '2022-01-02','a1f8e3b0-4c6d-4e5e-ab7a-2a8b9b9c8d6f','4390615e-1101-7209-9932-7020bbd556f1','a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f','a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f'),
('bfee750d-9b8c-4475-9373-08eff9b3ccb7','vente à credit', 20, 10000, 'd301ff83-2a62-4e6d-aa23-57c7825bcd57', '6190615e-1101-7209-9932-7020bbd556f2', null, '2022-01-01', '2022-01-02','d9e2f1c2-8b3a-4f6c-bd5e-1a7b8a7c6d5e','4390615e-1101-7209-9932-7020bbd556f2','d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e','d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e'),
('0e7cea07-d09e-4f79-be6c-6dd27aecbce6', 'achat express', 5, 2000,'fdbff692-6b8d-43ab-a196-fcc3114b2daa',  '6190615e-1101-7209-9932-7020bbd556f3', null, '2022-01-01', '2022-01-02','b3a7c8d6-1e2f-3d4c-cb6a-9e8f0a7b6c5d','4390615e-1101-7209-9932-7020bbd556f3','b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d','b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d'),
('decaedc8-a908-4cad-bdd6-0403a2614f22','inventaire', 5, 2000, 'fdbff692-6b8d-43ab-a196-fcc3114b2daa',  '6190615e-1101-7209-9932-7020bbd556f4', null, '2022-01-01', '2022-01-02','f0a7b6c5-d4e3f2a1-8d9c-7d6e-5f4a3b2c1e0d','4390615e-1101-7209-9932-7020bbd556f3','f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d','f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d');


INSERT INTO etats (id, libelle,description, datecreation, datemodification) VALUES
('e190615e-1101-7209-9932-7020bbd556f1','En cours','En cours', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f2','Valide','Valide', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f3','Rejete','Rejete', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f4','En attente','En attente', '2022-01-01', '2022-01-02');

-- Insérer les données dans la table 'roles' en se basant sur la classe RolesEntity
INSERT INTO roles (id, titre, description, etat, datecreation,datemodification) VALUES
('5190615e-1101-7209-9932-7020bbd556f1','vendeur','personnel au contact du client', true, '2022-01-01','2022-01-02'),
('6130615e-1101-7209-9932-7020bbd556f2','traiteur','Personnel administratif', true, '2022-01-01','2022-01-02'),
('6191615e-1101-7209-9932-7020bbd556f3','marcheur','commercial sur le terrain', true, '2022-01-01','2022-01-02');

-- Insérer les données dans la table 'jouerRole' en se basant sur la classe JouerRoleEntity
INSERT INTO jouerRoles (id,personnels_id, roles_id,datedebut,datefin, datecreation, datemodification) VALUES
('d76fd017-cceb-4926-8705-380b08ad9c6a','4390615e-1101-7209-9932-7020bbd556f1','5190615e-1101-7209-9932-7020bbd556f1','2024-01-01',null,'2022-01-01','2022-01-02'),
('d39bcc09-ffe6-48d1-8582-f1173671d59f','4390615e-1101-7209-9932-7020bbd556f2','6130615e-1101-7209-9932-7020bbd556f2','2024-01-01',null,'2022-01-01','2022-01-02'),
('9183d626-0c1a-4f70-8556-1c417d5feb91','4390615e-1101-7209-9932-7020bbd556f3','6191615e-1101-7209-9932-7020bbd556f3','2024-01-01',null,'2022-01-01','2022-01-02');

--inserer données dans la table 'traiter'
INSERT INTO traiter (documents_id,missions_id) values
('0190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f1'),
('0190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f2'),
('0190615e-1101-7209-9932-7020bbd556f2','3190615e-1101-7209-9932-7020bbd556f2'),
('0190615e-1101-7209-9932-7020bbd556f3','3190615e-1101-7209-9932-7020bbd556f3');

--INSERT INTO mouvpreco (id_precomouvements, id_mouvements) VALUES
--('6290615e-1101-7209-9932-7020bbd556f1', '97d314f5-84ad-47cd-874d-9c55f0e45790'),
--('6290615e-1101-7209-9932-7020bbd556f2', 'bfee750d-9b8c-4475-9373-08eff9b3ccb7'),
--('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', '0e7cea07-d09e-4f79-be6c-6dd27aecbce6'),
--('77b8577f-6d26-4376-af30-a3c8f75a9194', 'decaedc8-a908-4cad-bdd6-0403a2614f22');


 INSERT INTO documentspromotions (documents_id,promotions_id) VALUES
 ('0190615e-1101-7209-9932-7020bbd556f1','1979bd79-f81b-498b-b247-e7b9bbb3f600'),
 ('0190615e-1101-7209-9932-7020bbd556f8','1979bd79-f81b-498b-b247-e7b9bbb3f602'),
 ('0190615e-1101-7209-9932-7020bbd556f5','1979bd79-f81b-498b-b247-e7b9bbb3f601');

 INSERT INTO famillespromotions(familles_id,promotions_id) VALUES
 ('f190615e-1101-7209-9932-7020bbd556f1','1979bd79-f81b-498b-b247-e7b9bbb3f602'),
 ('f190615e-1101-7209-9932-7020bbd556f3','1979bd79-f81b-498b-b247-e7b9bbb3f601'),
 ('f190615e-1101-7209-9932-7020bbd556f4','1979bd79-f81b-498b-b247-e7b9bbb3f600');

 INSERT INTO ordreetats (id,datecreation,datemodification,datefinvote,ordre,etats_id)  VALUES
 ('97d314a5-84ad-47cd-874d-9c55f0e45790','2022-01-01','2022-01-02','2022-01-02',2,'e190615e-1101-7209-9932-7020bbd556f3'),
 ('97d314a5-84ad-47cd-874d-9c55f0e45791','2022-01-01','2022-01-02','2022-01-02',5,'e190615e-1101-7209-9932-7020bbd556f2'),
 ('97d314a5-84ad-47cd-874d-9c55f0e45792','2022-01-01','2022-01-02','2022-01-02',8,'e190615e-1101-7209-9932-7020bbd556f1');


INSERT INTO rattacher (personnes_id,rattacher_id) VALUES
('1979bd79-f71b-498b-b247-e7b9bbb3f600','d301ff83-2a62-4e6d-aa23-57c7825bcd57'),
('fdbff692-6b8d-43ab-a196-fcc3114b2daa','0618e585-f82a-4d5f-af1c-54f880d766d3');

INSERT INTO promotions (id,datedebut,datefin,codeunique,typeremise,valeurremise,datecreation,datemodification,distributeurs_id) VALUES
('1979bd79-f81b-498b-b247-e7b9bbb3f600','2022-01-01','2022-01-01','R5','garantie',54.25,'2000-04-10','2022-01-01','1979bd79-f71b-498b-b247-e7b9bbb3f600'),
('1979bd79-f81b-498b-b247-e7b9bbb3f601','2022-01-01','2022-01-01','R54','virement',74.25,'2004-08-10','2022-01-01','0618e585-f82a-4d5f-af1c-54f880d766d3'),
('1979bd79-f81b-498b-b247-e7b9bbb3f602','2022-01-01','2022-01-01','R45','devoir',95.23,'2002-10-10','2022-01-01','d301ff83-2a62-4e6d-aa23-57c7825bcd57');

INSERT INTO concerner (distributeurs_id,precomouvementsqtes_id) VALUES
('1979bd79-f71b-498b-b247-e7b9bbb3f600','a97eb081-62f6-4617-ba29-64dc8593a9ff'),
('0618e585-f82a-4d5f-af1c-54f880d766d3','a87eb081-62f6-4617-ba29-64dc8593a9ff'),
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','a77eb081-62f6-4617-ba29-64dc8593a9ff');

INSERT INTO violer (mouvements_id,precomouvements_id) VALUES
('97d314f5-84ad-47cd-874d-9c55f0e45790','6290615e-1101-7209-9932-7020bbd556f6'),
('bfee750d-9b8c-4475-9373-08eff9b3ccb7','6290615e-1101-7209-9932-7020bbd556f7'),
('0e7cea07-d09e-4f79-be6c-6dd27aecbce6','6290615e-1101-7209-9932-7020bbd556f8');

INSERT INTO respecter (mouvements_id , precomouvements_id) VALUES
('97d314f5-84ad-47cd-874d-9c55f0e45790','6290615e-1101-7209-9932-7020bbd556f1'),
('bfee750d-9b8c-4475-9373-08eff9b3ccb7','6290615e-1101-7209-9932-7020bbd556f2'),
('0e7cea07-d09e-4f79-be6c-6dd27aecbce6','17ee6932-2fcd-4b93-9c4c-0a4dbf659bff');

INSERT INTO parcours (id , libelle , datecreation, datemodification) VALUES
('1900bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol','2022-01-02','2022-01-02'),
('1900bd79-f71b-498b-b247-e7b9bbb3f601','Cartouche d''encre','2020-01-08','2022-01-02'),
('1900bd79-f71b-498b-b247-e7b9bbb3f602','Scanner','2014-01-04','2022-01-02');

INSERT INTO etapes (id , libelle , etat , datemodification , parcours_id) VALUES
('1901bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol',true,'2022-01-02','1900bd79-f71b-498b-b247-e7b9bbb3f602'),
('1901bd79-f71b-498b-b247-e7b9bbb3f601','Cartouche d''encre',true,'2020-01-08','1900bd79-f71b-498b-b247-e7b9bbb3f600'),
('1901bd79-f71b-498b-b247-e7b9bbb3f602','Scanner',true,'2014-01-04','1900bd79-f71b-498b-b247-e7b9bbb3f601');

INSERT INTO parcours (id , libelle , datecreation, datemodification) VALUES
('1900bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol','2022-01-02','2022-01-02'),
('1900bd79-f71b-498b-b247-e7b9bbb3f601','Cartouche d''encre','2020-01-08','2022-01-02'),
('1900bd79-f71b-498b-b247-e7b9bbb3f602','Scanner','2014-01-04','2022-01-02');

INSERT INTO etapes (id , libelle , etat , datemodification , parcours_id) VALUES
('1901bd79-f71b-498b-b247-e7b9bbb3f600', 'Paracetamol',true,'2022-01-02','1900bd79-f71b-498b-b247-e7b9bbb3f602'),
('1901bd79-f71b-498b-b247-e7b9bbb3f601','Cartouche d''encre',true,'2020-01-08','1900bd79-f71b-498b-b247-e7b9bbb3f600'),
('1901bd79-f71b-498b-b247-e7b9bbb3f602','Scanner',true,'2014-01-04','1900bd79-f71b-498b-b247-e7b9bbb3f601');

INSERT INTO remplir(datefin,datecreation,datedebut,etat,droitajouter,droitmodifier,droitconsulter,droitvalider,roles_id,missions_id) VALUES
('2022-01-02','2022-01-02','2022-01-02',true,true,true,true,true,'6130615e-1101-7209-9932-7020bbd556f2','3190615e-1101-7209-9932-7020bbd556f1'),
('2022-01-02','2022-01-02','2022-01-02',true,true,true,true,true,'5190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f2'),
('2022-01-02','2022-01-02','2022-01-02',true,true,true,true,true,'6191615e-1101-7209-9932-7020bbd556f3','3190615e-1101-7209-9932-7020bbd556f3');

INSERT INTO ressourcespromotions (promotions_id,ressources_id) VALUES
('1979bd79-f81b-498b-b247-e7b9bbb3f600','6190615e-1101-7209-9932-7020bbd556f6'),
('1979bd79-f81b-498b-b247-e7b9bbb3f601','6190615e-1101-7209-9932-7020bbd556f4'),
('1979bd79-f81b-498b-b247-e7b9bbb3f602','6190615e-1101-7209-9932-7020bbd556f2');

INSERT INTO tickets(id,codecourt,datecreation,datemodification) VALUES
('be8ef47f-d5a2-4254-cd79-af860672553e','rr15','2022-03-02','2022-10-02'),
('be8ef47f-d5a2-4254-cd79-af860672554e','rr10','2002-01-02','2022-11-02'),
('be8ef47f-d5a2-4254-cd79-af860672555e','rr11','2014-01-12','2022-02-02'),
('be8ef47f-d5a2-4254-cd79-af860672556e','rr12','2023-10-25','2024-03-02'),
('be8ef47f-d5a2-4254-cd79-af860672557e','rr16','2020-11-15','2024-05-02'),
('be8ef47f-d5a2-4254-cd79-af860672558e','rr14','2022-01-02','2023-03-02');

INSERT INTO ticketsfilesattentes(id,etat,dateaffectation,tickets_id,filesattentes_id) VALUES
('be8ef47f-d7a2-4254-cd79-af860672553e',true,'2024-11-04','be8ef47f-d5a2-4254-cd79-af860672553e','f190615e-1101-7209-9932-7020bbd556f1'),
('be8ef47f-d7a2-4254-cd79-af860672554e',false,'2022-12-04','be8ef47f-d5a2-4254-cd79-af860672554e','f190615e-1101-7209-9932-7020bbd556f2'),
('be8ef47f-d7a2-4254-cd79-af860672555e',false,'2020-11-04','be8ef47f-d5a2-4254-cd79-af860672558e','f190615e-1101-7209-9932-7020bbd556f3'),
('be8ef47f-d7a2-4254-cd79-af860672556e',false,'2021-04-04','be8ef47f-d5a2-4254-cd79-af860672556e','f190615e-1101-7209-9932-7020bbd556f3'),
('be8ef47f-d7a2-4254-cd79-af860672557e',true,'2023-02-04','be8ef47f-d5a2-4254-cd79-af860672555e','f190615e-1101-7209-9932-7020bbd556f2'),
('be8ef47f-d7a2-4254-cd79-af860672558e',false,'2022-01-04','be8ef47f-d5a2-4254-cd79-af860672557e','f190615e-1101-7209-9932-7020bbd556f3');

INSERT INTO validations(id,code,etat,datecreation,datemodification,typevote,dureevote,typevalidation,roles_id) VALUES
('1901bd80-f71b-498b-b247-e7b9bbb3f602','o78','attente','2022-03-02','2022-10-02','multiple',14,'prioritaire','5190615e-1101-7209-9932-7020bbd556f1'),
('1901bd80-f71b-498b-b247-e7b9bbb3f601','o89','suppression','2002-01-02','2022-11-02','multiple',2,'prioritaire','6130615e-1101-7209-9932-7020bbd556f2'),
('1901bd80-f71b-498b-b247-e7b9bbb3f600', 'o85','traitement','2014-01-12','2022-02-02','multiple',5,'rechargeable','6191615e-1101-7209-9932-7020bbd556f3'),
('1901bd80-f71b-498b-b247-e7b9bbb3f603', 'o82','traitement','2023-10-25','2024-03-02','multiple',22,'prioritaire','5190615e-1101-7209-9932-7020bbd556f1'),
('1901bd80-f71b-498b-b247-e7b9bbb3f604', 'o84','traitement','2020-11-15','2024-05-02','multiple',40,'prioritaire','6191615e-1101-7209-9932-7020bbd556f3');

INSERT INTO docetats(id,ordre,datecreation,datemodification,validations_id,etats_id,documents_id,etapes_id) VALUES
('be8ef4af-d5a2-4254-cd79-af860672553e',15,'2022-03-02','2022-10-02','1901bd80-f71b-498b-b247-e7b9bbb3f602','e190615e-1101-7209-9932-7020bbd556f1','0190615e-1101-7209-9932-7020bbd556f1','1901bd79-f71b-498b-b247-e7b9bbb3f600'),
('be8ef4af-d5a2-4254-cd79-af860672554e',10,'2002-01-02','2022-11-02','1901bd80-f71b-498b-b247-e7b9bbb3f601','e190615e-1101-7209-9932-7020bbd556f2','0190615e-1101-7209-9932-7020bbd556f2','1901bd79-f71b-498b-b247-e7b9bbb3f601'),
('be8ef4af-d5a2-4254-cd79-af860672555e',11,'2014-01-12','2022-02-02','1901bd80-f71b-498b-b247-e7b9bbb3f600','e190615e-1101-7209-9932-7020bbd556f3','0190615e-1101-7209-9932-7020bbd556f3','1901bd79-f71b-498b-b247-e7b9bbb3f602'),
('be8ef4af-d5a2-4254-cd79-af860672556e',12,'2023-10-25','2024-03-02','1901bd80-f71b-498b-b247-e7b9bbb3f603','e190615e-1101-7209-9932-7020bbd556f4','0190615e-1101-7209-9932-7020bbd556f4','1901bd79-f71b-498b-b247-e7b9bbb3f602'),
('be8ef4af-d5a2-4254-cd79-af860672557e',16,'2020-11-15','2024-05-02','1901bd80-f71b-498b-b247-e7b9bbb3f604','e190615e-1101-7209-9932-7020bbd556f4','0190615e-1101-7209-9932-7020bbd556f5','1901bd79-f71b-498b-b247-e7b9bbb3f600');

INSERT INTO docetats_predecesseurs(docetats_id,predecesseur_id) VALUES
('be8ef4af-d5a2-4254-cd79-af860672553e','be8ef4af-d5a2-4254-cd79-af860672555e'),
('be8ef4af-d5a2-4254-cd79-af860672554e','be8ef4af-d5a2-4254-cd79-af860672556e');

INSERT INTO deltasoldes (id,comptes_id,exemplaires_id, montantavant, montantapres, datecreation, typemouvement) VALUES
('a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6','a1f8e3b0-4c6d-4e5e-7b7a-2a8b9b9c8d6f','a1f8e3b0-4c6d-4e5e-9b7a-2a8b9b9c8d6f', 100.50, 120.75, '2024-08-19', 'Dépôt'),
('b7c8d9e0-f1g2-3h4i-5b6k-7l8m9n0o1p21','d9e2f1c2-8b3a-4f6c-7d5e-1a7b8a7c6d5e','d9e2f1c2-8b3a-4f6c-9d5e-1a7b8a7c6d5e', 75.25, 60.00, '2024-08-20', 'Retrait'),
('b7c8d9e0-f1g2-3h4i-5c6k-7c8m9n0o1p21','b3a7c8d6-1e2f-3d4c-7b6a-9e8f0a7b6c5d','b3a7c8d6-1e2f-3d4c-5b6a-9e8f0a7b6c5d', 750.25, 740.00, '2024-08-20', 'Retrait'),
('b7c8d9e0-f1g2-3h4i-5d6k-7l8m9n0o1p21','f0a7b6c5-d4e3f2a1-7b9c-7d6e-5f4a3b2c1e0d','f0a7b6c5-d4e3f2a1-8b9c-7d6e-5f4a3b2c1e0d', 7.25, 640.00, '2024-08-20', 'Retrait');


