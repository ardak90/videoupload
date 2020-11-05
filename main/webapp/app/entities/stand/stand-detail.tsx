import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './stand.reducer';
import { IStand } from 'app/shared/model/stand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStandDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StandDetail extends React.Component<IStandDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { standEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Stand [<b>{standEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="ipAddress">Ip Address</span>
            </dt>
            <dd>{standEntity.ipAddress}</dd>
            <dt>
              <span id="storeName">Store Name</span>
            </dt>
            <dd>{standEntity.storeName}</dd>
            <dt>
              <span id="city">City</span>
            </dt>
            <dd>{standEntity.city}</dd>
          </dl>
          <Button tag={Link} to="/entity/stand" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/stand/${standEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ stand }: IRootState) => ({
  standEntity: stand.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StandDetail);
