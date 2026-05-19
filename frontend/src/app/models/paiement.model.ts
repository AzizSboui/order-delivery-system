export interface Paiement {
  id?: number;
  commandeId: number;
  date?: string;
  statut: string;
  mode: string;
}

export interface PaiementRequest {
  commandeId: number;
  mode: string;
}

export const MODES_PAIEMENT = ['CARTE_BANCAIRE', 'VIREMENT', 'PAYPAL', 'ESPECES', 'CHEQUE'];
export const STATUTS_PAIEMENT = ['EN_ATTENTE', 'VALIDE', 'REFUSE', 'REMBOURSE'];
