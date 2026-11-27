-- 1. Inserindo ou Atualizando Usuários
INSERT INTO users (id, name, email, password, phone_number, photo_url)
VALUES
    ('8f07e730-23e1-47b4-a20e-f667b0961303', 'ryan', 'ryan@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVym5W0.m2pWJbH8C./B5T0G', '11111111111', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F631cd830-0cb1-427b-a132-12ba91d2eae0.png'),
    ('3db8b175-d1e9-4c82-9849-7c136da927a2', 'vini malvadeza', 'vinimalvadeza@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVym5W0.m2pWJbH8C./B5T0G', '11111111111', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2Fcd8cd9e2-8539-4a1b-af16-f89de10b1fc8.png'),
    ('c7d1e892-1234-4a5b-8c9d-111122223333', 'Carlos Silva', 'carlos@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVym5W0.m2pWJbH8C./B5T0G', '11988887777', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F10b10cd7-1e2c-4a64-b540-9e76d3ad9710.png'),
    ('d8e2f903-5678-4b6c-9d0e-444455556666', 'Ana Souza', 'ana@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVym5W0.m2pWJbH8C./B5T0G', '11977776666', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F6a4926b4-f72a-4c47-baf4-fb142483be43.png')
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         phone_number = VALUES(phone_number),
                         photo_url = VALUES(photo_url);

-- 2. Inserindo ou Atualizando Garagens (Região do SENAI Suíço-Brasileira - Rua Bento Branco de Andrade Filho, 379)
INSERT INTO garages (id, user_id, name, address, latitude, longitude, description, available, price_per_day, price_per_hour, created_at, updated_at)
VALUES
    -- Garagem Base (Ryan - Em frente ao SENAI Suíço-Brasileira)
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Estacionamento Bento Branco SENAI', 'Rua Bento Branco de Andrade Filho, 350 - Jardim Dom Bosco, São Paulo - SP',
     -23.6480, -46.7215, 'Garagem coberta em frente ao SENAI Suíço-Brasileira, com segurança 24h, seguro total e monitoramento por câmeras.', TRUE, 35.00, 8.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Garagens Próximas (Raio de 200 m até 3.5 km da Escola)
    ('a1111111-1111-1111-1111-111111111111', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Park Ponte Transamérica', 'Rua Bento Branco de Andrade Filho, 700 - Santo Amaro, São Paulo - SP',
     -23.6505, -46.7222, 'Vaga coberta a 3 minutos a pé do SENAI e próximo à Ponte Transamérica.', TRUE, 40.00, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a2222222-2222-2222-2222-222222222222', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Estacionamento Teatro Alfa', 'Rua Bento Branco de Andrade Filho, 722 - Santo Amaro, São Paulo - SP',
     -23.6508, -46.7225, 'Vaga privativa com portão automático e seguro total.', TRUE, 50.00, 12.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a3333333-3333-3333-3333-333333333333', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Garagem Estação Santo Amaro', 'Av. Guido Caloi, 1000 - Santo Amaro, São Paulo - SP',
     -23.6545, -46.7195, 'Acesso rápido à Linha 5-Lilás e Linha 9-Esmeralda.', TRUE, 45.00, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a4444444-4444-4444-4444-444444444444', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Park Mário Lopes Leão', 'Av. Mário Lopes Leão, 1200 - Santo Amaro, São Paulo - SP',
     -23.6512, -46.7125, 'Vagas amplas para carros e motos com monitoramento 24h.', TRUE, 38.00, 9.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a5555555-5555-5555-5555-555555555555', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Garagem Largo Treze Centro', 'Rua Barão do Rio Branco, 350 - Santo Amaro, São Paulo - SP',
     -23.6538, -46.7075, 'Garagem coberta no centro comercial de Santo Amaro.', TRUE, 35.00, 8.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a6666666-6666-6666-6666-666666666666', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Shopping Mais Express Park', 'Rua Amador Bueno, 229 - Santo Amaro, São Paulo - SP',
     -23.6525, -46.7098, 'Estacionamento seguro ao lado do Shopping Mais e Terminal Santo Amaro.', TRUE, 42.00, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a7777777-7777-7777-7777-777777777777', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Park Chácara Santo Antônio', 'Rua Américo Brasiliense, 2150 - Chácara Santo Antônio, São Paulo - SP',
     -23.6355, -46.7120, 'Garagem residencial próxima aos centros empresariais da Chácara Santo Antônio.', TRUE, 55.00, 14.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a8888888-8888-8888-8888-888888888888', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'CENESP Marginal Pinheiros Park', 'Av. Maria Coelho Aguiar, 215 - Jardim São Luís, São Paulo - SP',
     -23.6475, -46.7315, 'Logo após a Ponte Transamérica, ideal para quem vem pela Marginal.', TRUE, 48.00, 11.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a9999999-9999-9999-9999-999999999999', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Garagem Metrô Adolfo Pinheiro', 'Av. Adolfo Pinheiro, 1000 - Santo Amaro, São Paulo - SP',
     -23.6465, -46.7025, 'Vaga individual coberta próxima à estação Adolfo Pinheiro.', TRUE, 45.00, 11.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('b1111111-1111-1111-1111-111111111111', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Alto da Boa Vista Residential Park', 'Rua Darwin, 400 - Alto da Boa Vista, São Paulo - SP',
     -23.6420, -46.6980, 'Rua tranquila e arborizada com vaga coberta em condomínio.', TRUE, 50.00, 12.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Garagens um pouco mais distantes na Zona Sul (Testes de Paginação e Raio Amplo)
    ('b2222222-2222-2222-2222-222222222222', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Morumbi Shopping Express Park', 'Av. Roque Petroni Júnior, 1089 - Jardim das Acácias, São Paulo - SP',
     -23.6230, -46.6985, 'Fácil acesso à Av. Berrini e Shopping Morumbi.', TRUE, 60.00, 15.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('b3333333-3333-3333-3333-333333333333', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Autódromo Interlagos Park', 'Av. Senador Teotônio Vilela, 261 - Interlagos, São Paulo - SP',
     -23.7010, -46.6970, 'Garagem ampla na Zona Sul próxima ao Autódromo de Interlagos.', TRUE, 30.00, 7.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         address = VALUES(address),
                         latitude = VALUES(latitude),
                         longitude = VALUES(longitude),
                         description = VALUES(description),
                         available = VALUES(available),
                         price_per_day = VALUES(price_per_day),
                         price_per_hour = VALUES(price_per_hour),
                         updated_at = CURRENT_TIMESTAMP;

-- 2.1 Inserindo Características das Garagens
INSERT IGNORE INTO garage_features (garage_id, feature)
VALUES
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Coberto'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Segurança 24h'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Câmeras'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Acessibilidade');

-- 3. Inserindo Veículo do Vini
INSERT IGNORE INTO vehicles (id, user_id, license_plate, year, brand, model, color)
VALUES (
    '38543986-adfe-41b5-ae25-d0767a3538c3',
    '3db8b175-d1e9-4c82-9849-7c136da927a2',
    'BRA2E22',
    2022,
    'FORD',
    'SEDAN',
    'RED'
);

-- 4. Inserindo Reservas do Vini na Garagem do Ryan
INSERT IGNORE INTO bookings (id, driver_id, garage_id, vehicle_id, start_date_time, end_date_time, total_amount, booking_type, status, created_at)
VALUES
    ('e097b41c-9f91-4d99-897e-b122fbbbbaa0',
     '3db8b175-d1e9-4c82-9849-7c136da927a2',
     '8ad50250-bf0b-4daf-b56f-d0db1703c991',
     '38543986-adfe-41b5-ae25-d0767a3538c3',
     '2026-10-01 14:00:00',
     '2026-10-01 18:00:00',
     0.00,
     'HOURLY',
     'PENDING',
     CURRENT_TIMESTAMP),

    ('f108c52d-0a02-5e00-908f-c233ecccbbb1',
     '3db8b175-d1e9-4c82-9849-7c136da927a2',
     '8ad50250-bf0b-4daf-b56f-d0db1703c991',
     '38543986-adfe-41b5-ae25-d0767a3538c3',
     '2026-09-15 10:00:00',
     '2026-09-15 12:00:00',
     30.00,
     'HOURLY',
     'COMPLETED',
     CURRENT_TIMESTAMP),

    ('f209d63e-1b03-6f11-019f-d344fdddccc2',
     '3db8b175-d1e9-4c82-9849-7c136da927a2',
     '8ad50250-bf0b-4daf-b56f-d0db1703c991',
     '38543986-adfe-41b5-ae25-d0767a3538c3',
     '2026-09-20 08:00:00',
     '2026-09-20 12:00:00',
     60.00,
     'HOURLY',
     'COMPLETED',
     CURRENT_TIMESTAMP);

-- 5. Inserindo Sala de Chat vinculada à Garagem e à Reserva
INSERT IGNORE INTO chat_rooms (id, garage_id, booking_id, driver_id, owner_id, created_at)
VALUES (
    '2b5823f7-bd4f-4391-90f1-285be893030e',
    '8ad50250-bf0b-4daf-b56f-d0db1703c991',
    'e097b41c-9f91-4d99-897e-b122fbbbbaa0',
    '3db8b175-d1e9-4c82-9849-7c136da927a2',
    '8f07e730-23e1-47b4-a20e-f667b0961303',
    CURRENT_TIMESTAMP
);

-- 6. Inserindo Avaliação da Garagem (Feita pelo Motorista Vini)
INSERT IGNORE INTO garage_reviews (id, booking_id, driver_id, garage_id, rating, comment, created_at)
VALUES (
    'd4a11111-2222-3333-4444-555555555555',
    'f108c52d-0a02-5e00-908f-c233ecccbbb1',
    '3db8b175-d1e9-4c82-9849-7c136da927a2',
    '8ad50250-bf0b-4daf-b56f-d0db1703c991',
    5,
    'Excelente garagem! Bem em frente ao SENAI e o dono foi super atencioso.',
    CURRENT_TIMESTAMP
);

-- 7. Inserindo Avaliação do Motorista (Feita pelo Dono Ryan)
INSERT IGNORE INTO driver_reviews (id, booking_id, owner_id, driver_id, rating, comment, created_at)
VALUES (
    'e5b22222-3333-4444-5555-666666666666',
    'f108c52d-0a02-5e00-908f-c233ecccbbb1',
    '8f07e730-23e1-47b4-a20e-f667b0961303',
    '3db8b175-d1e9-4c82-9849-7c136da927a2',
    5,
    'Ótimo motorista! Pontual e deixou o veículo perfeitamente estacionado.',
    CURRENT_TIMESTAMP
);

-- 8. Inserindo ou Atualizando Notificações dos Usuários
INSERT INTO notifications (id, user_id, title, message, is_read, created_at)
VALUES
    ('c1111111-1111-1111-1111-111111111111',
     '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Nova Reserva Confirmada!',
     'Sua garagem Estacionamento Bento Branco SENAI foi reservada por vini malvadeza.',
     FALSE,
     CURRENT_TIMESTAMP),

    ('c2222222-2222-2222-2222-222222222222',
     '3db8b175-d1e9-4c82-9849-7c136da927a2',
     'Reserva Confirmada!',
     'Seu pagamento foi aprovado! Sua vaga na garagem Estacionamento Bento Branco SENAI está garantida.',
     TRUE,
     CURRENT_TIMESTAMP),

    ('c3333333-3333-3333-3333-333333333333',
     'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Bem-vindo ao EstacionaAI!',
     'Sua garagem Park Ponte Transamérica já está visível no mapa para os motoristas.',
     FALSE,
     CURRENT_TIMESTAMP),

    ('c4444444-4444-4b6c-9d0e-444455556666',
     'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Perfil Atualizado',
     'Sua foto de perfil e dados foram atualizados com sucesso.',
     TRUE,
     CURRENT_TIMESTAMP)
    ON DUPLICATE KEY UPDATE
                         message = VALUES(message);