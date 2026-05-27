from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    """Application configuration from environment variables and optional `.env`."""

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8",
        extra="ignore",
    )

    PROJECT_NAME: str = "Platziflix"
    VERSION: str = "0.1.0"
    DATABASE_URL: str = (
        "postgresql+psycopg2://postgres:postgres@localhost:5432/platziflix"
    )


settings = Settings()
