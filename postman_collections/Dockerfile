FROM python:3.8-buster as base
FROM base as builder

RUN apt-get -qq update \
    && apt-get install -y --no-install-recommends g++ \
    && apt-get install -y --no-install-recommends bash

RUN python -m pip install --upgrade pip && pip install newman

FROM base
COPY --from=builder /install /usr/local

COPY . .
ENTRYPOINT ["bash", "load_generator.sh"]