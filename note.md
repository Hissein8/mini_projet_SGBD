# Répartition des tâches & Guide de collaboration Git

> ⚠️ **IMPORTANT : Ne jamais coder directement sur la branch `main` !**
> Chaque membre travaille sur sa propre branch et soumet une Pull Request pour merger.

---

## Étapes communes à tous (à faire en premier)

### 1. Cloner le dépôt

```bash
git clone https://github.com/Hissein8/mini_projet_SGBD
cd mini_projet_SGBD
```

### 2. Vérifier qu'on est bien sur `main`

```bash
git branch
```

La branche `main` doit être marquée d'un `*`.

### 3. Créer et basculer sur sa branche personnelle

```bash
# P1
git checkout -b feature/employe-contrat

# P2
git checkout -b feature/presence-conge

# P3
git checkout -b feature/paie-tableau-bord
```

### 4. Vérifier qu'on est bien sur sa branche

```bash
git branch
```

Ta branche doit être marquée d'un `*` — si tu vois `* main` tu es au mauvais endroit.

### 5. Télécharger le driver MySQL

- Aller sur : https://dev.mysql.com/downloads/connector/j/
- Choisir **Platform Independent** → Télécharger le **ZIP**
- Extraire et copier le `.jar` dans le dossier `lib/`

### 6. Créer le fichier `.env`

À la racine du projet :

```
DB_URL=jdbc:mysql://localhost:3306/rh_entreprise
DB_USER=root
DB_PASSWORD=
```

> Le `.env` est dans le `.gitignore` — chacun crée le sien localement, il ne sera jamais pushé.

### 7. Tester la connexion

Depuis le dossier `src/` :

```bash
javac -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main.java connexion/Connexion.java
java -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main
```

La console doit afficher **"Connexion réussie !"** avant de commencer à coder.

---

## Répartition des tâches

---

### P1 — Gestion des Employés & Contrats

**Fichiers à créer :**

- `src/modeles/Employe.java`
- `src/modeles/Contrat.java`
- `src/menus/MenuEmploye.java`
- `src/menus/MenuContrat.java`

**Fonctionnalités à implémenter :**

- Ajouter un employé
- Modifier un employé
- Afficher la fiche complète d'un employé (contrat, absences, congés)
- Lister les CDD expirant dans moins de 30 jours
- Afficher les employés sans contrat actif

**Rapport :**

- Partie Employés et Contrats (description, captures d'écran des fonctionnalités)

**Test de sa partie :**

Compiler tous les fichiers :

```bash
javac -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main.java connexion/Connexion.java modeles/Employe.java modeles/Contrat.java menus/MenuEmploye.java menus/MenuContrat.java
java -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main
```

Tester les fonctionnalités suivantes et faire des captures d'écran :

1. Ajouter un nouvel employé → Vérifier dans votre outil de gestion MySQL (HeidiSQL, MySQL Workbench, phpMyAdmin, ligne de commande...) qu'il apparaît dans la table
2. Modifier l'email d'un employé → vérifier la mise à jour
3. Afficher la fiche d'un employé → vérifier que contrat et département s'affichent
4. Lister les CDD expirant bientôt → vérifier que les bons employés apparaissent

---

### P2 — Gestion des Présences & Congés

**Fichiers à créer :**

- `src/modeles/Presence.java`
- `src/modeles/Conge.java`
- `src/menus/MenuPresence.java`
- `src/menus/MenuConge.java`

**Fonctionnalités à implémenter :**

- Enregistrer les présences/absences d'une journée pour un département entier
- Afficher les présences d'un employé
- Soumettre une demande de congé
- Approuver ou refuser un congé
- Afficher le solde de congés restants d'un employé

**Rapport :**

- Partie Présences et Congés (description, captures d'écran des fonctionnalités)

**Test de sa partie :**

Compiler tous les fichiers :

```bash
javac -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main.java connexion/Connexion.java modeles/Presence.java modeles/Conge.java menus/MenuPresence.java menus/MenuConge.java
java -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main
```

Tester les fonctionnalités suivantes et faire des captures d'écran :

1. Enregistrer une présence → vérifier dans votre outil de gestion MySQL (HeidiSQL, MySQL Workbench, phpMyAdmin, ligne de commande...)
2. Enregistrer une absence → vérifier le statut
3. Soumettre une demande de congé → vérifier le statut "demande"
4. Approuver un congé → vérifier le statut "approuve"
5. Afficher le solde de congés d'un employé

---

### P3 — Paie & Tableau de bord

**Fichiers à créer :**

- `src/modeles/Bulletin.java`
- `src/menus/MenuBulletin.java`
- `src/menus/MenuTableauBord.java`
- `src/Main.java` (menu principal)

**Fonctionnalités à implémenter :**

- Générer un bulletin de paie (calcul automatique des retenues et salaire net)
- Afficher les bulletins d'un employé
- Tableau de bord : masse salariale du mois par département
- Tableau de bord : taux d'absentéisme sur les 3 derniers mois
- Tableau de bord : congés en attente d'approbation

**Rapport :**

- Partie Paie et Tableau de bord (description, captures d'écran des fonctionnalités)

**Test de sa partie :**

Compiler tous les fichiers :

```bash
javac -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main.java connexion/Connexion.java modeles/Bulletin.java menus/MenuBulletin.java menus/MenuTableauBord.java
java -cp ".;../lib/mysql-connector-j-9.7.0.jar" Main
```

Tester les fonctionnalités suivantes et faire des captures d'écran :

1. Générer un bulletin → vérifier le calcul : retenues = salaire_brut × nb_absences / 22
2. Afficher les bulletins d'un employé
3. Tableau de bord → masse salariale du mois en cours
4. Tableau de bord → taux d'absentéisme
5. Tableau de bord → congés en attente

---

## Envoyer son travail sur GitHub

Une fois les tests réussis et les captures faites :

### 1. Vérifier les fichiers modifiés

```bash
git status
```

### 2. Ajouter ses fichiers

```bash
git add .
```

### 3. Faire un commit

```bash
# P1
git commit -m "feat: gestion employés et contrats"

# P2
git commit -m "feat: gestion présences et congés"

# P3
git commit -m "feat: paie et tableau de bord"
```

### 4. Pusher sa branche

```bash
# P1
git push origin feature/employe-contrat

# P2
git push origin feature/presence-conge

# P3
git push origin feature/paie-tableau-bord
```

### 5. Créer une Pull Request sur GitHub

- Aller sur https://github.com/Hissein8/mini_projet_SGBD
- Cliquer sur **"Compare & pull request"**
- Ajouter un titre et une description
- Cliquer sur **"Create pull request"**

> C'est P1 (le chef de projet) qui se chargera de merger les Pull Requests sur `main` après vérification.
