DROP TABLE prenotazione;
DROP TABLE utente;
DROP TABLE campo;

CREATE TABLE utente(
	id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
	dataNascita TIMESTAMP NOT NULL
);

CREATE TABLE prenotazione(
                             id SERIAL PRIMARY KEY,
                             id_utente INTEGER REFERENCES Utente(id) ON DELETE CASCADE NOT NULL,
                             durataprenotazione INTEGER NOT NULL,
                             duratadeadlinecancellazione INTEGER NOT NULL,
                             numeropartecipanti INTEGER NOT NULL,
                             quota INTEGER NOT NULL,
                             dataprenotazione TIMESTAMP NOT NULL,
                             datafineprenotazione TIMESTAMP NOT NULL,
                             id_campo INTEGER REFERENCES Campo(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE campo(
                             id SERIAL PRIMARY KEY,
                             codice VARCHAR(50) NOT NULL,
                             tipologia VARCHAR(50) NOT NULL
);