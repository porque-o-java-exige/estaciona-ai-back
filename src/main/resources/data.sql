-- 1. Inserindo Usuários (Ryan, Vini, Carlos e Ana com Fotos de Perfil)
INSERT IGNORE INTO users (id, name, email, password, phone_number, photo_url)
VALUES
    ('8f07e730-23e1-47b4-a20e-f667b0961303', 'ryan', 'ryan@email.com', '123456789', '11111111111', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F631cd830-0cb1-427b-a132-12ba91d2eae0.png'),
    ('3db8b175-d1e9-4c82-9849-7c136da927a2', 'vini malvadeza', 'vinimalvadeza@email.com', '123456789', '11111111111', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2Fcd8cd9e2-8539-4a1b-af16-f89de10b1fc8.png'),
    ('c7d1e892-1234-4a5b-8c9d-111122223333', 'Carlos Silva', 'carlos@email.com', '123456789', '11988887777', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F10b10cd7-1e2c-4a64-b540-9e76d3ad9710.png'),
    ('d8e2f903-5678-4b6c-9d0e-444455556666', 'Ana Souza', 'ana@email.com', '123456789', '11977776666', 'https://estacionaaistorage.blob.core.windows.net/estaciona-ai-images/users/2F6a4926b4-f72a-4c47-baf4-fb142483be43.png');

-- 2. Inserindo Garagens
INSERT IGNORE INTO garages (id, user_id, name, address, latitude, longitude, description, available, price_per_day, price_per_hour, created_at, updated_at)
VALUES
    -- Garagem Base (Ryan)
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Estacionamento Central Paulista', 'Avenida Paulista, 1000 - Bela Vista, São Paulo - SP',
     -23.5615, -46.6558, 'Garagem coberta, com segurança 24h, seguro total e monitoramento por câmeras. Próximo ao metrô.', TRUE, 45.00, 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Garagens Próximas (Raio de 0.4 km até 5 km da Av. Paulista)
    ('a1111111-1111-1111-1111-111111111111', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Estacionamento MASP Consolação', 'Rua da Consolação, 2200 - Consolação, São Paulo - SP',
     -23.5580, -46.6590, 'Vaga coberta e individual próximo ao MASP.', TRUE, 50.00, 12.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a2222222-2222-2222-2222-222222222222', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Park Oscar Freire', 'Rua Oscar Freire, 900 - Cerqueira César, São Paulo - SP',
     -23.5640, -46.6660, 'Vaga privativa em condomínio de luxo nos Jardins.', TRUE, 70.00, 18.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a3333333-3333-3333-3333-333333333333', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Garagem Shopping Higienópolis', 'Av. Higienópolis, 618 - Higienópolis, São Paulo - SP',
     -23.5480, -46.6530, 'Excelente localização perto da FAAP e do Shopping.', TRUE, 60.00, 15.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a4444444-4444-4444-4444-444444444444', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Estacionamento Paraíso Metro', 'Rua Vergueiro, 1400 - Paraíso, São Paulo - SP',
     -23.5700, -46.6430, 'A 2 minutos a pé do metrô Paraíso. Segurança total.', TRUE, 40.00, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a5555555-5555-5555-5555-555555555555', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Garagem Praça da República', 'Praça da República, 180 - Centro, São Paulo - SP',
     -23.5430, -46.6400, 'Garagem ampla no centro histórico.', TRUE, 35.00, 8.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a6666666-6666-6666-6666-666666666666', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Itaim Premium Parking', 'Rua Joaquim Floriano, 400 - Itaim Bibi, São Paulo - SP',
     -23.5830, -46.6780, 'Vaga coberta no coração financeiro do Itaim.', TRUE, 80.00, 20.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a7777777-7777-7777-7777-777777777777', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Park Vila Mariana ESPM', 'Rua Álvaro Alvim, 120 - Vila Mariana, São Paulo - SP',
     -23.5850, -46.6350, 'Garagem residencial ao lado da faculdade ESPM/Belas Artes.', TRUE, 45.00, 10.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a8888888-8888-8888-8888-888888888888', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Faria Lima Financial Park', 'Av. Brig. Faria Lima, 1800 - Pinheiros, São Paulo - SP',
     -23.5670, -46.6900, 'Garagem com manobrista e seguro contra roubo.', TRUE, 90.00, 22.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('a9999999-9999-9999-9999-999999999999', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Vila Madalena Art Parking', 'Rua Harmonia, 300 - Vila Madalena, São Paulo - SP',
     -23.5530, -46.6920, 'Ótimo para quem vai curtir a noite na Vila Madalena.', TRUE, 50.00, 12.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('b1111111-1111-1111-1111-111111111111', 'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Moema Passarinheiros Park', 'Rua Sabiá, 500 - Moema, São Paulo - SP',
     -23.6000, -46.6600, 'Fácil acesso ao Parque Ibirapuera e aeroporto de Congonhas.', TRUE, 55.00, 14.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    -- Garagens Distantes (Testes de Paginação e Raio Amplo)
    ('b2222222-2222-2222-2222-222222222222', '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Guarulhos Airport Express Park', 'Rod. Hélio Smidt, s/n - Cumbica, Guarulhos - SP',
     -23.4320, -46.4690, 'Garagem para longas estadias com traslado gratuito até o terminal.', TRUE, 30.00, 6.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

    ('b3333333-3333-3333-3333-333333333333', 'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Estacionamento Campinas Centro', 'Av. Francisco Glicério, 1000 - Centro, Campinas - SP',
     -22.9055, -47.0608, 'Garagem central na cidade de Campinas.', TRUE, 35.00, 7.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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
    -- 1. Reserva Pendente (Para testar bloqueio por status diferente de COMPLETED)
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

    -- 2. Reserva Concluída Com Avaliações Existentes (Para testar GET e bloqueio por duplicidade)
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

    -- 3. Reserva Concluída SEM Avaliações (Para testar criação de novas avaliações via POST no Postman!)
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
    'Excelente garagem! Muito organizada e o dono foi super atencioso.',
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

-- 8. Inserindo Notificações dos Usuários
INSERT IGNORE INTO notifications (id, user_id, title, message, is_read, created_at)
VALUES
    -- Notificação para o Ryan (Proprietário)
    ('c1111111-1111-1111-1111-111111111111',
     '8f07e730-23e1-47b4-a20e-f667b0961303',
     'Nova Reserva Confirmada!',
     'Sua garagem Estacionamento Central Paulista foi reservada por vini malvadeza.',
     FALSE,
     CURRENT_TIMESTAMP),

    -- Notificação para o Vini Malvadeza (Motorista)
    ('c2222222-2222-2222-2222-222222222222',
     '3db8b175-d1e9-4c82-9849-7c136da927a2',
     'Reserva Confirmada!',
     'Seu pagamento foi aprovado! Sua vaga na garagem Estacionamento Central Paulista está garantida.',
     TRUE,
     CURRENT_TIMESTAMP),

    -- Notificação para o Carlos Silva (Proprietário)
    ('c3333333-3333-3333-3333-333333333333',
     'c7d1e892-1234-4a5b-8c9d-111122223333',
     'Bem-vindo ao EstacionaAI!',
     'Sua garagem Estacionamento MASP Consolação já está visível no mapa para os motoristas.',
     FALSE,
     CURRENT_TIMESTAMP),

    -- Notificação para a Ana Souza (Proprietária)
    ('c4444444-4444-4444-4444-444444444444',
     'd8e2f903-5678-4b6c-9d0e-444455556666',
     'Perfil Atualizado',
     'Sua foto de perfil e dados foram atualizados com sucesso.',
     TRUE,
     CURRENT_TIMESTAMP);