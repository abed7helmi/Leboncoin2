export interface Message {
  id: number;
  contenu: string;
  lu: boolean;
  dateEnvoi: string;
  expediteur: {
    id: number;
    nom: string;
    prenom: string;
  };
  destinataire: {
    id: number;
    nom: string;
    prenom: string;
  };
  annonce: {
    id: number;
    titre: string;
  };
}

export interface Conversation {
  annonceId: number;
  annonceTitre: string;
  annonceImage: string | null;
  interlocuteurId: number;
  interlocuteurNom: string;
  dernierMessage: string;
  dateDernierMessage: string;
  nonLus: number;
}
