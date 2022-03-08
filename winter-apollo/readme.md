## USAGE

```yaml
winter:
  apollo:
    enabled: true
apollo:
  env: DEV
  meta: http:localhost:8080 # Your apollo config server address
  bootstrap:
    enabled: true
    namespace: application
  deafult-change-listener: # The bean with annotation @ConfigurationProperties will update its values when config changed when enabled
    enabled: true
```