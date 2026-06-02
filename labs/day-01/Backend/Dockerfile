FROM python:3.11-slim

WORKDIR /app

RUN pip install uv

COPY pyproject.toml uv.lock* README.md ./

ENV UV_PROJECT_ENVIRONMENT="/usr/local/"

RUN uv sync --frozen --no-dev

COPY app ./app
COPY specs ./specs

EXPOSE 8000

CMD ["uv", "run", "uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000", "--reload"]
