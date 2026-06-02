from fastapi import FastAPI

from app.core.config import settings

app = FastAPI(title=settings.PROJECT_NAME, version=settings.VERSION)


@app.get("/")
def read_root() -> dict[str, str]:
    return {"message": f"Bienvenido a {settings.PROJECT_NAME}"}


@app.get("/health")
def health() -> dict[str, str]:
    return {
        "status": "ok",
        "service": settings.PROJECT_NAME,
        "version": settings.VERSION,
    }
