    -- 1. Inserindo Usuários (Ryan e Vini)
INSERT IGNORE INTO users (id, name, email, password, phone_number)
VALUES
    ('8f07e730-23e1-47b4-a20e-f667b0961303', 'ryan', 'ryan@email.com', '123456789', '11111111111'),
    ('3db8b175-d1e9-4c82-9849-7c136da927a2', 'vini malvadeza', 'vinimalvadeza@email.com', '123456789', '11111111111');

-- 2. Inserindo Garagem do Ryan
INSERT IGNORE INTO garages (id, user_id, name, address, latitude, longitude, description, available, price_per_day, price_per_hour, created_at, updated_at)
VALUES (
    '8ad50250-bf0b-4daf-b56f-d0db1703c991',
    '8f07e730-23e1-47b4-a20e-f667b0961303',
    'Estacionamento Central Paulista',
    'Avenida Paulista, 1000 - Bela Vista, São Paulo - SP',
    -23.5615,
    -46.6558,
    'Garagem coberta, com segurança 24h, seguro total e monitoramento por câmeras. Próximo ao metrô.',
    TRUE,
    45.00,
    0.00,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 2.1 Inserindo Características da Garagem
INSERT IGNORE INTO garage_features (garage_id, feature)
VALUES
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Coberto'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Segurança 24h'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Câmeras'),
    ('8ad50250-bf0b-4daf-b56f-d0db1703c991', 'Acessibilidade');

-- 3. Inserindo Veículo do Vini (substituído owner_id por user_id)
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

-- 4. Inserindo Reserva do Vini na Garagem do Ryan
INSERT IGNORE INTO bookings (id, driver_id, garage_id, vehicle_id, start_date_time, end_date_time, total_amount, booking_type, status, created_at)
VALUES (
    'e097b41c-9f91-4d99-897e-b122fbbbbaa0',
    '3db8b175-d1e9-4c82-9849-7c136da927a2',
    '8ad50250-bf0b-4daf-b56f-d0db1703c991',
    '38543986-adfe-41b5-ae25-d0767a3538c3',
    '2026-10-01 14:00:00',
    '2026-10-01 18:00:00',
    0.00,
    'HOURLY',
    'PENDING',
    CURRENT_TIMESTAMP
);

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