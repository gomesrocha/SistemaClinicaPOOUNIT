/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author root
 */
public interface GenericDao<T, ID extends Serializable> {

    public Class<T> getObjectCLass();

    public T salvar(T object);

    public T pesquisarPorId(ID id);

    public T atualizar(T object);

    public void excluir(T object);

    public List<T> todos();
}
