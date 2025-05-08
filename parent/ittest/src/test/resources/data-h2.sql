INSERT INTO document.documents(
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

--insertion dans la table 'filesattentes'
INSERT INTO document.filesattentes (id,  etat, datecreation, datemodification) VALUES
('f190615e-1101-7209-9932-7020bbd556f1', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f2',  true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f3',  true, '2022-01-01', '2022-01-02');

-- Insertion dans la table service
INSERT INTO document.services (id, libelle, description, codeunique, etat,localisation, datecreation, datemodification,filesattentes_id)
VALUES
('2190615e-1101-7209-9932-7020bbd556f1', 'Consultation', 'bien', 'S1', true, 'douala', '1972-06-12', '1990-03-07','f190615e-1101-7209-9932-7020bbd556f1'),
('2190615e-1101-7209-9932-7020bbd556f2', 'Laboratoire', 'bien', 'S2', true, 'douala', '1990-08-06', '1990-03-07','f190615e-1101-7209-9932-7020bbd556f2'),
('2190615e-1101-7209-9932-7020bbd556f3', 'Pharmacie', 'bien', 'S3', true, 'douala', '2000-03-07', '1990-03-07','f190615e-1101-7209-9932-7020bbd556f3');

-- Insertion dans la table mission
INSERT INTO document.missions (id, libelle, description, etat, datecreation, datemodification, services_id)
VALUES
('3190615e-1101-7209-9932-7020bbd556f1', 'Encaissement', 'recu de paiement lié à une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1'),
('3190615e-1101-7209-9932-7020bbd556f2', 'Resultat Labo', 'Communiquer les résultats du labo aux patients', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f1'),
('3190615e-1101-7209-9932-7020bbd556f3', 'Hospitalisation', 'bon d''entrée et de sortie est une mission', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2'),
('3190615e-1101-7209-9932-7020bbd556f4', 'Prelevement Labo', 'Prélévement fait par laboratoire', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f2'),
('3190615e-1101-7209-9932-7020bbd556f5', 'Consultation Spécialiste', 'Consultation faite par un médecin', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3'),
('3190615e-1101-7209-9932-7020bbd556f6', 'Consultation', 'Consultation faite par une infirmière', true, '2000-03-07', '1990-03-07', '2190615e-1101-7209-9932-7020bbd556f3');

INSERT INTO document.attributs (id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut)
VALUES
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Taille', 'Description Taille', TRUE, '2022-01-01', '2022-01-02', 'Text', null),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Poids', 'Description Poids', TRUE, '2022-01-01', '2022-01-02', 'Number', null),
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Age', 'Description age', TRUE, '2022-01-01', '2022-01-02', 'Text', null),
('a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Sexe', 'Sexe ', TRUE, '2022-01-01', '2022-01-02', 'Text', null),
('a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Teint', 'Coloration', TRUE, '2022-01-01', '2022-01-02', 'Text', 'Noir'),
('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'Groupe sangin', 'Groupe sangin', TRUE, '2022-01-01', '2022-01-02', 'Radio', 'A, A+, A-, B, B+, B-, AB, AB+, AB-, O, O+, O-'),
('a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17', 'Allergies', 'Allergies connues ', TRUE, '2022-01-01', '2022-01-02', 'Textarea', null),
('a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18', 'Nom', 'Nom de la personne', TRUE, '2022-01-01', '2022-01-02', 'Text', null),
('a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19', 'Adresse', 'Adresse postale', TRUE, '2022-01-01', '2022-01-02', 'Text', null),
('a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20', 'Date de naissance', 'Date de naissance', TRUE, '2022-01-01', '2022-01-02', 'Date', null);

-- Insertion dans la table 'categories'
INSERT INTO document.categories (id, libelle, ordre, etat, datecreation, datemodification,documents_id)
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
INSERT INTO document.associer (categories_id, attributs_id,ordre,obligatoire)
VALUES
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',1,true),
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',2,true),
('36b843d8-f8a7-4b76-9980-1add9edc2364', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',3,false),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',1,true),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',2,false),
('be9ef47f-d5a2-4254-ab79-af860672553e', 'a2eebc99-9c0b-4ef8-bb6d-6bb9bd380a20',3,true),
('be8ef47f-d5a2-4254-ab79-af860672553e', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a13',1,true),
('be8ef47f-d5a2-4254-ab79-af860672553e', 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14',1,true),
('be8ef57f-d5a2-4254-ab79-af860672553e', 'a4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14',1,true),
('be8ef56f-d5a2-4254-ab79-af860672553e', 'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a15',1,true),
('be8ef59f-d5a2-4254-ab79-af860672553e', 'a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a16',1,true),
('be8ef59f-d5a3-4254-ab79-af860672553e', 'a7eebc99-9c0b-4ef8-bb6d-6bb9bd380a17',1,true),
('be8ef59f-d5a4-4254-ab79-af860672553e', 'a8eebc99-9c0b-4ef8-bb6d-6bb9bd380a18',1,true),
('be8ef59f-d5a4-4254-ab99-af860672553e', 'a9eebc99-9c0b-4ef8-bb6d-6bb9bd380a19',1,true);

-- Insertion dans la table 'constituer'
INSERT INTO document.constituer (documents_id, attributs_id)
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
INSERT INTO document.precomouvements (id, libelle, etat, typemouvement, datecreation, datemodification) VALUES
('6290615e-1101-7209-9932-7020bbd556f1', 'Inventaire', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f2', 'Vente', true, 'Reduire', '2022-01-01', '2022-01-02'),
('17ee6932-2fcd-4b93-9c4c-0a4dbf659bff', 'Achat', true, 'Ajout', '2022-01-01', '2022-01-02'),
('77b8577f-6d26-4376-af30-a3c8f75a9194', 'interdiction Infirmière', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f5', 'Don', true, 'Neutre', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f6', 'Perte', true, 'Reduire', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f7', 'Retour', true, 'Ajout', '2022-01-01', '2022-01-02'),
('6290615e-1101-7209-9932-7020bbd556f8', 'Sortie magasin', true, 'Reduire', '2022-01-01', '2022-01-02');

---- Insertion dans la table 'familles'
INSERT INTO document.familles (id, libelle, description, etat, datecreation, datemodification) VALUES
('f190615e-1101-7209-9932-7020bbd556f1', 'Medicaments', 'Medicaments', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f2', 'Consommables Informatiques', 'Consommables Informatiques', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f3', 'BioMedical', 'BioMedical', true, '2022-01-01', '2022-01-02'),
('f190615e-1101-7209-9932-7020bbd556f4', 'Accessoires', 'Accessoires', true, '2022-01-01', '2022-01-02');

--
---- Insertion dans la table 'ressources'
INSERT INTO document.ressources (id, libelle, description, etat, datecreation, datemodification,quantite, prixentree,prixsortie,seuilalerte,unite, familles_id) VALUES
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
INSERT INTO document.precomouvementsqtes (id, qtemin, qtemax, montantmin, montantmax, precomouvements_id, ressources_id, datecreation, datemodification) VALUES
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
INSERT INTO document.sapplique (precomouvementsqtes_id,familles_id)
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
INSERT INTO document.suivre (precomouvements_id, documents_id)
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
INSERT INTO document.personnes (id,adresse,mail,telephone,qrcodevalue,datecreation,datemodification) VALUES
('1979bd79-f71b-498b-b247-e7b9bbb3f600','Bastos','abd@yah.fr','655454545','qrcode1','2022-01-01','2022-01-02'),
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','Bastos','dbc@yah.fr','655454545','qrcode2','2022-01-01','2022-01-02'),
('0618e585-f82a-4d5f-af1c-54f880d766d3','Bastos','pnj@gma.com','655454545','qrcode3','2022-01-01','2022-01-02'),
('fdbff692-6b8d-43ab-a196-fcc3114b2daa','Bastos','sg@sg.fr','655454545','qrcode4','2022-01-01','2022-01-02'),
('4390615e-1101-7209-9932-7020bbd556f1','Douala','tagnewillie@gmail.com','655455487','qrcode6','2022-01-01','2022-01-02'),
('4390615e-1101-7209-9932-7020bbd556f2','Douala','peteralan@gmail.com','888455487','qrcode7','2022-03-01','2022-01-02'),
('4390615e-1101-7209-9932-7020bbd556f3','Douala','dombogilles@gmail.com','999455487','qrcode8','2022-04-01','2022-01-02'),
('0195fe35-0742-7db1-ab55-f16e0c621ac0','Douala','steve@gmail.com','999495487','qrcode9','2022-04-01','2022-01-02'),
('0195c9e8-bbb7-7418-8c4a-69500c816c5f','Yaoundé','dede@yeoo.fr', '9800994433','qrcode5', '2015-03-31',	'2025-03-25');

-- Insérer les données dans la table 'distributeur' en se basant sur la classe DistributeurEntity
INSERT INTO document.distributeurs (distributeurs_id, code, raisonsociale, datemodification) VALUES
('1979bd79-f71b-498b-b247-e7b9bbb3f600','d190615','ENEO','2022-01-01'),
('0618e585-f82a-4d5f-af1c-54f880d766d3','d190616','Brasserie','2022-01-01'),
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','d190619','UCB','2022-01-01'),
('fdbff692-6b8d-43ab-a196-fcc3114b2daa','d190614','Pharmacam','2022-01-01');

INSERT INTO document.etats (id, libelle,description, datecreation, datemodification) VALUES
('e190615e-1101-7209-9932-7020bbd556f1','En cours','En cours', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f2','Valide','Valide', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f3','Rejete','Rejete', '2022-01-01', '2022-01-02'),
('e190615e-1101-7209-9932-7020bbd556f4','En attente','En attente', '2022-01-01', '2022-01-02');

-- Insérer les données dans la table 'roles' en se basant sur la classe RolesEntity
INSERT INTO document.roles (id, titre, description, etat, datecreation,datemodification) VALUES
('5190615e-1101-7209-9932-7020bbd556f1','vendeur','personnel au contact du client', true, '2022-01-01','2022-01-02'),
('6130615e-1101-7209-9932-7020bbd556f2','traiteur','Personnel administratif', true, '2022-01-01','2022-01-02'),
('6191615e-1101-7209-9932-7020bbd556f3','marcheur','commercial sur le terrain', true, '2022-01-01','2022-01-02');

INSERT INTO document.personnels (personnels_id, dateentree, nom, datenaissance, datesortie, prenom, sexe)
VALUES
    ('4390615e-1101-7209-9932-7020bbd556f1', '2025-04-10', 'Tagne', '2000-04-10', NULL, 'Willy', 'M'),
    ('4390615e-1101-7209-9932-7020bbd556f2', '2024-05-09', 'Peter', '2004-08-10',  NULL, 'Alan', 'M'),
    ('4390615e-1101-7209-9932-7020bbd556f3', '2020-11-17', 'Dombo', '2002-10-10',  NULL, 'Gilles', 'M');

-- Insérer les données dans la table 'jouerRole' en se basant sur la classe JouerRoleEntity
INSERT INTO document.jouerRoles (id,personnels_id, roles_id,datedebut,datefin, datecreation, datemodification) VALUES
('d76fd017-cceb-4926-8705-380b08ad9c6a','4390615e-1101-7209-9932-7020bbd556f1','5190615e-1101-7209-9932-7020bbd556f1','2024-01-01',null,'2022-01-01','2022-01-02'),
('d39bcc09-ffe6-48d1-8582-f1173671d59f','4390615e-1101-7209-9932-7020bbd556f2','6130615e-1101-7209-9932-7020bbd556f2','2024-01-01',null,'2022-01-01','2022-01-02'),
('9183d626-0c1a-4f70-8556-1c417d5feb91','4390615e-1101-7209-9932-7020bbd556f3','6191615e-1101-7209-9932-7020bbd556f3','2024-01-01',null,'2022-01-01','2022-01-02');

--inserer données dans la table 'traiter'
INSERT INTO document.traiter (documents_id,missions_id) values
('0190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f1'),
('0190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f2'),
('0190615e-1101-7209-9932-7020bbd556f2','3190615e-1101-7209-9932-7020bbd556f2'),
('0190615e-1101-7209-9932-7020bbd556f3','3190615e-1101-7209-9932-7020bbd556f3');

--inserer données dans la table 'personnesPhysiques'
INSERT INTO document.personnesphysique (personnesphysique_id, nom, prenom, sexe, datenaissance, datemodification) VALUES
('0195c9e8-bbb7-7418-8c4a-69500c816c5f', 'Phil',	'dede',	'M',	'1999-03-31',	'2025-03-25'),
('0195fe35-0742-7db1-ab55-f16e0c621ac0', 'Steve',	'dede',	'M',	'1999-03-31',	'2025-03-25');

insert into document.rattacher (personnes_id, rattacher_id) values
('0195c9e8-bbb7-7418-8c4a-69500c816c5f','0618e585-f82a-4d5f-af1c-54f880d766d3'),
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','0618e585-f82a-4d5f-af1c-54f880d766d3'),
('d301ff83-2a62-4e6d-aa23-57c7825bcd57','0195fe35-0742-7db1-ab55-f16e0c621ac0'),
('0195c9e8-bbb7-7418-8c4a-69500c816c5f','4390615e-1101-7209-9932-7020bbd556f1');

insert  into document.remplir(id,roles_id,missions_id,etat,droitajouter,droitmodifier,droitconsulter,droitvalider,datefin,datedebut,datecreation) values
('d76fd017-cceb-4926-8705-380b08ad9c6a','5190615e-1101-7209-9932-7020bbd556f1','3190615e-1101-7209-9932-7020bbd556f1',true,true,true,true,true,'2024-01-01','2024-01-01','2022-01-01'),
('d39bcc09-ffe6-48d1-8582-f1173671d59f','6130615e-1101-7209-9932-7020bbd556f2','3190615e-1101-7209-9932-7020bbd556f2',true,true,true,true,true,'2024-01-01','2024-01-01','2022-01-01'),
('9183d626-0c1a-4f70-8556-1c417d5feb91','6191615e-1101-7209-9932-7020bbd556f3','3190615e-1101-7209-9932-7020bbd556f3',true,true,true,true,true,'2024-01-01','2024-01-01','2022-01-01');


