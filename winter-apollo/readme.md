## USAGE

```yaml
winter:
  apollo:
    enabled: true
apollo:
  env: DEV
  meta: http:localhost:8080 # Your apollo config server address
  enable-default-listener: true # The bean with annotation @ConfigurationProperties will update its values when config changed when enabled
  bootstrap:
    enabled: true
    namespace: application
```