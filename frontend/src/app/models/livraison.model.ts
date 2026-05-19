export interface Livraison {
  id?: number;
  commandeId: number;
  transporteurId?: number;
  transporteurNom?: string;
  dateLivraison?: string;
  statut: string;
  cout?: number;
}

export interface LivraisonRequest {
  commandeId: number;
  transporteurId?: number;
  dateLivraison?: string;
  cout?: number;
}
