describe('My First Test', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'localhost:8080/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('name');
  });
});
});

describe('My Second Test', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'localhost:8080/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('address');
  });
});
});

describe('My Third Test', () => {
  it('GET', function() {
      cy.request({
          method: 'GET',
          url: 'localhost:8080/hospitals/1',
      }).then(function(response) {
          expect(response.body).have.property('registerDate');
  });
});
});