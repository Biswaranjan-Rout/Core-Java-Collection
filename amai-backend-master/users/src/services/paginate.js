 const paginate = (page, pageSize) =>{
    page = page || 0
    pageSize = pageSize || 10
    const offset = page * pageSize;
    const limit = offset + pageSize;
    return {
      offset:offset,
      limit:limit
    };
};

  module.exports = paginate