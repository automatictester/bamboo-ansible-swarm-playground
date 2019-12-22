ansible-playbook \
    -i ansible/inventory/bamboo/hosts \
    ansible/playbooks/deploy_simple_app.yaml \
    -e simple_app_version=${bamboo.inject.version}