package com.store.storebackend;

import com.store.storebackend.entity.Annonce;
import com.store.storebackend.entity.Categorie;
import com.store.storebackend.entity.Image;
import com.store.storebackend.entity.Utilisateur;
import com.store.storebackend.enums.EtatAnnonce;
import com.store.storebackend.enums.RoleUtilisateur;
import com.store.storebackend.repository.AnnonceRepository;
import com.store.storebackend.repository.CategorieRepository;
import com.store.storebackend.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class StoreBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreBackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UtilisateurRepository utilisateurRepo,
                               CategorieRepository categorieRepo,
                               AnnonceRepository annonceRepo) {
        return args -> {

            // ======== CATEGORIES ========
            Categorie electronique = createCategorie(categorieRepo, "Electronique", "Appareils electroniques");
            Categorie mobilier = createCategorie(categorieRepo, "Mobilier", "Meubles et decoration");
            Categorie vetements = createCategorie(categorieRepo, "Vetements", "Mode homme et femme");
            Categorie sport = createCategorie(categorieRepo, "Sport", "Equipements et accessoires sportifs");
            Categorie vehicules = createCategorie(categorieRepo, "Vehicules", "Voitures, motos et accessoires");
            Categorie immobilier = createCategorie(categorieRepo, "Immobilier", "Locations et ventes immobilieres");
            Categorie loisirs = createCategorie(categorieRepo, "Loisirs", "Jeux, livres et divertissement");

            // ======== UTILISATEURS ========

            // Admin
            Utilisateur admin = createUtilisateur(utilisateurRepo,
                    "Admin", "Super", "admin@mystore.com", "admin123",
                    "0600000000", "Paris", RoleUtilisateur.ADMIN);

            // Alice
            Utilisateur alice = createUtilisateur(utilisateurRepo,
                    "Dupont", "Alice", "alice.dupont@email.com", "motdepasse123",
                    "0612345678", "Paris", RoleUtilisateur.UTILISATEUR);

            // Bob
            Utilisateur bob = createUtilisateur(utilisateurRepo,
                    "Martin", "Bob", "bob.martin@email.com", "motdepasse456",
                    "0698765432", "Lyon", RoleUtilisateur.UTILISATEUR);

            // Clara
            Utilisateur clara = createUtilisateur(utilisateurRepo,
                    "Bernard", "Clara", "clara.bernard@email.com", "motdepasse789",
                    "0655443322", "Marseille", RoleUtilisateur.UTILISATEUR);

            // David
            Utilisateur david = createUtilisateur(utilisateurRepo,
                    "Petit", "David", "david.petit@email.com", "motdepasse101",
                    "0677889900", "Toulouse", RoleUtilisateur.UTILISATEUR);

            // Emma
            Utilisateur emma = createUtilisateur(utilisateurRepo,
                    "Leroy", "Emma", "emma.leroy@email.com", "motdepasse202",
                    "0611223344", "Bordeaux", RoleUtilisateur.UTILISATEUR);

            // ======== ANNONCES ALICE (Paris) ========
            createAnnonce(annonceRepo, "iPhone 14 Pro",
                    "iPhone 14 Pro 128Go, excellent etat, vendu avec boite et chargeur d'origine. Batterie a 92%.",
                    "750.00", "Paris", alice, electronique, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1678685888221-cda773a3dcdb?w=600");

            createAnnonce(annonceRepo, "MacBook Air M2",
                    "MacBook Air M2 2022, 8Go RAM, 256Go SSD. Tres bon etat, cycle de charge : 45.",
                    "900.00", "Paris", alice, electronique, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600");

            createAnnonce(annonceRepo, "AirPods Pro 2",
                    "AirPods Pro 2eme generation avec boitier MagSafe. Utilises 3 mois, comme neufs.",
                    "180.00", "Paris", alice, electronique, EtatAnnonce.VENDU,
                    "https://images.unsplash.com/photo-1606220945770-b5b6c2c55bf1?w=600");

            createAnnonce(annonceRepo, "Veste en cuir vintage",
                    "Veste en cuir veritable, taille M, style vintage annees 80. Quelques traces qui font le charme.",
                    "95.00", "Paris", alice, vetements, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=600");

            // ======== ANNONCES BOB (Lyon) ========
            createAnnonce(annonceRepo, "Canape cuir noir",
                    "Canape 3 places en cuir noir, quelques traces d'usure. Tres confortable, ideal pour salon.",
                    "350.00", "Lyon", bob, mobilier, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=600");

            createAnnonce(annonceRepo, "Table basse en chene",
                    "Table basse en chene massif, dimensions 120x60cm. Design scandinave, parfait etat.",
                    "180.00", "Lyon", bob, mobilier, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1533090481720-856c6e3c1fdc?w=600");

            createAnnonce(annonceRepo, "VTT Rockrider ST 530",
                    "VTT Rockrider 27.5 pouces, cadre aluminium, freins a disque. Ideal randonnee et chemin.",
                    "280.00", "Lyon", bob, sport, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1576435728678-68d0fbf94e91?w=600");

            createAnnonce(annonceRepo, "Raquette de tennis Wilson",
                    "Raquette Wilson Pro Staff, cordee, grip neuf. Utilisee une saison.",
                    "65.00", "Lyon", bob, sport, EtatAnnonce.VENDU,
                    "https://images.unsplash.com/photo-1622279457486-62dcc4a431d6?w=600");

            // ======== ANNONCES CLARA (Marseille) ========
            createAnnonce(annonceRepo, "Samsung Galaxy S23",
                    "Samsung Galaxy S23 Ultra 256Go, couleur phantom black. Ecran impeccable, vendu avec coque.",
                    "680.00", "Marseille", clara, electronique, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=600");

            createAnnonce(annonceRepo, "Bibliothèque IKEA Billy",
                    "Bibliothèque Billy blanche, 80x28x202cm. Demontee, prete a emporter. Bon etat.",
                    "40.00", "Marseille", clara, mobilier, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1594620302200-9a762244a156?w=600");

            createAnnonce(annonceRepo, "Robe d'ete fleurie",
                    "Robe longue fleurie, taille S/M, portee une seule fois. Tissu leger, parfaite pour l'ete.",
                    "25.00", "Marseille", clara, vetements, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600");

            createAnnonce(annonceRepo, "Planche de surf 7'2",
                    "Planche de surf funboard 7'2, ideal debutant/intermediaire. Quelques traces de wax.",
                    "220.00", "Marseille", clara, sport, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1531722569936-825d3dd91b15?w=600");

            // ======== ANNONCES DAVID (Toulouse) ========
            createAnnonce(annonceRepo, "Peugeot 208 Style 2019",
                    "Peugeot 208 1.2 PureTech 82cv, 45000km, revision faite, controle technique OK. Couleur grise.",
                    "12500.00", "Toulouse", david, vehicules, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?w=600");

            createAnnonce(annonceRepo, "Scooter Yamaha NMAX 125",
                    "Yamaha NMAX 125cc, 2021, 8000km. Entretien a jour chez concessionnaire. Pare-brise et top case inclus.",
                    "3200.00", "Toulouse", david, vehicules, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1558981806-ec527fa84c39?w=600");

            createAnnonce(annonceRepo, "PlayStation 5 + 2 manettes",
                    "PS5 edition standard avec 2 manettes DualSense et 3 jeux (Spider-Man, God of War, FIFA).",
                    "420.00", "Toulouse", david, loisirs, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1606144042614-b2417e99c4e3?w=600");

            createAnnonce(annonceRepo, "Collection Harry Potter",
                    "Integrale Harry Potter 7 tomes, edition Gallimard poche. Tres bon etat, non annotes.",
                    "35.00", "Toulouse", david, loisirs, EtatAnnonce.VENDU,
                    "https://images.unsplash.com/photo-1618666012174-83b441a7f636?w=600");

            createAnnonce(annonceRepo, "Bureau gaming LED",
                    "Bureau gaming 140x60cm avec eclairage LED RGB, passe-cables et support casque integres.",
                    "150.00", "Toulouse", david, mobilier, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1616588589676-62b3d4ff6a10?w=600");

            // ======== ANNONCES EMMA (Bordeaux) ========
            createAnnonce(annonceRepo, "iPad Air 5eme gen",
                    "iPad Air M1, 64Go WiFi, bleu ciel. Avec Apple Pencil 2 et Smart Folio. Etat neuf.",
                    "520.00", "Bordeaux", emma, electronique, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600");

            createAnnonce(annonceRepo, "Tapis de yoga Manduka",
                    "Tapis Manduka PRO 6mm, couleur noir. Antiderapant, ideal pour yoga et pilates.",
                    "55.00", "Bordeaux", emma, sport, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1601925260368-ae2f83cf8b7f?w=600");

            createAnnonce(annonceRepo, "Manteau laine camel",
                    "Manteau long en laine melangee, couleur camel, taille 38. Porte quelques fois, comme neuf.",
                    "85.00", "Bordeaux", emma, vetements, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1539533113208-f6df8cc8b543?w=600");

            createAnnonce(annonceRepo, "Guitare acoustique Yamaha",
                    "Guitare acoustique Yamaha F310, parfaite pour debuter. Housse et accordeur inclus.",
                    "110.00", "Bordeaux", emma, loisirs, EtatAnnonce.ACTIVE,
                    "https://images.unsplash.com/photo-1510915361894-db8b60106cb1?w=600");

            createAnnonce(annonceRepo, "Etagere murale design",
                    "Lot de 3 etageres murales en bois et metal noir. Style industriel, fixations incluses.",
                    "45.00", "Bordeaux", emma, mobilier, EtatAnnonce.DESACTIVE,
                    "https://images.unsplash.com/photo-1532372576444-dda954194ad0?w=600");

            System.out.println("=== 22 annonces inserees pour 6 utilisateurs ===");
        };
    }

    private Categorie createCategorie(CategorieRepository repo, String nom, String description) {
        Categorie c = new Categorie();
        c.setNom(nom);
        c.setDescription(description);
        return repo.save(c);
    }

    private Utilisateur createUtilisateur(UtilisateurRepository repo,
                                           String nom, String prenom, String email, String mdp,
                                           String telephone, String ville, RoleUtilisateur role) {
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setEmail(email);
        u.setMotDePasse(mdp);
        u.setTelephone(telephone);
        u.setVille(ville);
        u.setRole(role);
        return repo.save(u);
    }

    private void createAnnonce(AnnonceRepository repo, String titre, String description,
                                String prix, String ville, Utilisateur vendeur, Categorie categorie,
                                EtatAnnonce etat, String imageUrl) {
        Annonce a = new Annonce();
        a.setTitre(titre);
        a.setDescription(description);
        a.setPrix(new BigDecimal(prix));
        a.setVille(ville);
        a.setVendeur(vendeur);
        a.setCategorie(categorie);
        a.setEtat(etat);

        Image img = new Image();
        img.setUrl(imageUrl);
        img.setDescription(titre);
        img.setOrdre(1);
        img.setAnnonce(a);
        a.getImages().add(img);

        repo.save(a);
    }
}
