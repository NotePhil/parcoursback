INSERT INTO documents(
	id, titre, description, etat,typemouvement,afficherunite,afficherdistributeur,prixeditable,contientressources,afficherprix, datecreation, datemodification)
	VALUES 
('1234','Fiche de soin avec poids avec teint','fiche de soin',True,'Neutre',true,true,true,true,true,NULL,NULL),
('2345','Fiche de traite','traite',True,'Neutre',true,true,true,true,true,'2023-03-31','2023-03-31'),
('2345png','Fiche de traite Test','traite test',True,'Ajout',true,true,true,true,true,NULL,NULL),
('2345png01','Fiche de Test','traite test',True,'Neutre',true,true,true,true,true,NULL,NULL),
('2345png02','Fiche de Test 2345-png-02','traite test 2345-png-02',True,'Neutre',true,true,true,true,true,NULL,NULL),
('2345png03','Fiche de Test 2345-png-03','traite test 2345-png-03',True,'Neutre',true,true,true,true,true,NULL,NULL),
('nom01','Fiche de Test','traite test',True,'Reduire',true,true,true,true,true,NULL,NULL),
('png04','Fiche de Test 4','traite test',True,'Neutre',true,true,true,true,true,NULL,NULL);

INSERT INTO attributs(
	id, titre, description, etat, datecreation, datemodification, type, valeurpardefaut)
	VALUES
('1234','TAILLES','tailles',True,NULL,NULL,'Double',NULL),
('1234de','TAILLE revisée','taille revisée',True,NULL,NULL,'Double',NULL),
('1234png','Colle','colle',True,NULL,NULL,'Double',NULL),
('1234png08','png-08-colle','png-08-colle',True,NULL,NULL,'Double',NULL),
('2345','teint','teint du nouveau',False,NULL,NULL,'Int',NULL),
('3456','SEXES','SEXE',True,NULL,NULL,'String',NULL);

INSERT INTO constituer(
	documents_id, attributs_id)
	VALUES 
('1234','1234de'),
('1234','2345'),
('2345','1234'),
('2345png','1234png'),
('2345png','1234png08'),
('2345png','3456'),
('2345png02','1234png'),
('2345png02','1234png08'),
('2345png02','3456'),
('2345png03','1234'),
('2345png03','1234png'),
('2345png03','3456'),
('png04','1234png'),
('png04','3456');

-- Insertions pour la table 'category'
INSERT INTO categories (id,ordre, libelle, documents_id)
    VALUES  ('1','1', 'Fiche de traite Test', '1234'),
            ('2','2', 'Lit hopital', '1234'),
            ('3','3', 'Don organes', '1234'),
            ('4','4', 'don de sang', '2345'),
            ('5','5', 'Économie et comptabilité', '2345'),
            ('6','6', 'Musique malade', '2345'),
            ('7','7', 'Cinéma', '2345'),
            ('8','8', 'Sport', '2345'),
            ('9','9', 'Cuisine', '2345'),
            ('19','19', 'Cuisine 19', '2345png02'),
            ('10','10', 'Voyage', '2345');



INSERT INTO associer (attributs_id, categories_id, obligatoire, ordre)
    VALUES ('1234', '1',true,0),
            ('2345', '2',false,0),
            ('3456', '3',false,0),
            ('3456', '4',false,0),
            ('3456', '5',true,0),
            ('3456', '6',false,0),
            ('1234png', '7',false,0),
            ('1234png', '8',false,0),
            ('2345', '9',false,0),
            ('3456', '10',false,0),
            ('1234png08', '19',false,0),
            ('1234png', '19',true,1),
            ('3456', '19',false,2);


