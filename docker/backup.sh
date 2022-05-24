docker exec -i progetto-db-1 pg_dump -U postgres postgres > backup.sql
echo "Backup Ended"
