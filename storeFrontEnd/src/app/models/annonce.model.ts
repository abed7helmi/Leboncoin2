export interface ImageAnnonce {
  id: number;
  url: string;
  description: string;
  ordre: number;
}

export interface Annonce {
  id: number;
  titre: string;
  description: string;
  prix: number;
  ville: string;
  etat: string;
  dateCreation: string;
  images: ImageAnnonce[];
  vendeur: {
    id: number;
    nom: string;
    prenom: string;
    ville: string;
  };
  categorie: {
    id: number;
    nom: string;
  };
}
