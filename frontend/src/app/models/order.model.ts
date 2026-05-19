export interface LigneCommande {
  produitId: number;
  produitNom?: string;
  quantite: number;
  prixUnitaire: number;
  sousTotal?: number;
}

export interface Commande {
  id?: number;
  date?: string;
  statut: string;
  montantTotal?: number;
  clientId: number;
  clientNom?: string;
  lignes: LigneCommande[];
}

export interface CommandeRequest {
  clientId: number;
  lignes: { produitId: number; quantite: number; prixUnitaire?: number }[];
}
