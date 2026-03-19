-- Initial schema for recipe manager

CREATE TABLE categories (
    category_id   BIGSERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE UNIQUE INDEX idx_category_name_unique ON categories(name);

CREATE TABLE recipes (
    recipe_id            BIGSERIAL PRIMARY KEY,
    category_id          BIGINT REFERENCES categories(category_id) ON DELETE SET NULL,
    title                VARCHAR(255) NOT NULL,
    description          TEXT,
    preparation_time_min INT CHECK (preparation_time_min > 0),
    image_url            TEXT,
    created_at           TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_recipe_title ON recipes(title);
CREATE INDEX idx_recipe_category_id ON recipes(category_id);

CREATE TABLE ingredients (
    ingredient_id BIGSERIAL PRIMARY KEY,
    recipe_id     BIGINT NOT NULL REFERENCES recipes(recipe_id) ON DELETE CASCADE,
    name          VARCHAR(255) NOT NULL,
    quantity      VARCHAR(100) NOT NULL,
    unit          VARCHAR(50)
);
CREATE INDEX idx_ingredient_name ON ingredients(name);

CREATE TABLE steps (
    step_id     BIGSERIAL PRIMARY KEY,
    recipe_id   BIGINT NOT NULL REFERENCES recipes(recipe_id) ON DELETE CASCADE,
    position    INT NOT NULL,
    description TEXT NOT NULL,
    UNIQUE (recipe_id, position)
);
