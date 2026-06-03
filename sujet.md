10. # Sujet 10 : Gestion d’une Entreprise et de sa Paie
## 10.1. Contexte
Une PME souhaite moderniser et structurer le suivi de ses ressources humaines : employés,
départements, contrats de travail, présences/absences, congés et calcul des salaires
mensuels. Le directeur RH doit pouvoir générer les bulletins de paie et suivre les absences
non justifiées.
## 10.2. Entités et règles de gestion
- Departement : code, nom, chef de departement, budget annuel
- Poste : code, intitule, salaire de base, niveau de responsabilite
- Employe : matricule, nom, prenom, date de naissance, date d’embauche, email,
telephone, departement, poste
- Contrat : employe, type (CDI/CDD/Stage), date de debut, date de fin, salaire brut
- Presence : employe, date, heure d’arrivee, heure de depart, statut (present/absent/conge)
- Conge : employe, date de debut, date de fin, type (annuel/maladie/maternite),
statut (demande/approuve/refuse)
- Bulletin : employe, mois, annee, salaire brut, nb jours travailles, nb absences injustifiees,
retenues, salaire net
Règles de gestion :
- Chaque absence injustifiée entraîne une retenue de 1/22 du salaire brut mensuel
- Un employé ne peut pas avoir deux contrats actifs simultanément
- Le solde de congés annuels est de 30 jours par an ; un congé refusé ou annulé restitue
les jours
- Un congé ne peut être approuvé que si l’employé a un contrat actif
## 10.3. Partie 1 : Modélisation
Réaliser le MCD selon Merise puis déduire le MLD complet.
## 10.4. Partie 2 : Base de données MySQL
Création (LDD)
Créer la base de données rh_entreprise et toutes ses tables avec les contraintes
appropriées.
Alimentation (LMD)
Insérer au minimum : 4 départements, 8 postes, 20 employés, 20 contrats, 30 présences,
10 congés, 20 bulletins.

Requêtes de consultation
1. Masse salariale mensuelle par département (GROUP BY + SUM)
2. Employés ayant plus de 3 absences injustifiées ce mois (HAVING)
3. Employés dont le contrat CDD expire dans moins de 30 jours
4. Solde de congés restants par employé pour l’année en cours
5. Employé le mieux payé par département (sous-requête corrélée)
6. Taux d’absentéisme par département sur les 3 derniers mois
## 10.5. Partie 3 : Gestion des droits (LCD)
* Créer deux utilisateurs :
— rh_manager : tous les droits sur toutes les tables de la base
— employe_role : SELECT uniquement sur Conge et Bulletin (ses propres données
via une vue si possible)

## 10.6. Partie 4 : Application cliente
Implémenter les fonctionnalités suivantes :
1. Gestion des employés : ajouter, modifier, afficher la fiche complète d’un employé
(contrat, absences, congés)
2. Enregistrer les présences : saisir les présences/absences d’une journée pour un
département entier
3. Gérer les congés : soumettre une demande, approuver ou refuser, déduire du solde
disponible
4. Générer un bulletin de paie : calculer automatiquement les retenues pour absences
injustifiées et le salaire net
5. Suivi des contrats : lister les CDD expirant bientôt, afficher les employés sans
contrat actif
6. Tableau de bord RH : masse salariale du mois, taux d’absentéisme, congés en
attente d’approbation
Bonne chance !
24